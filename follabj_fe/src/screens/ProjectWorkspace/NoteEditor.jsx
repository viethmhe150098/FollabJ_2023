import React, { useState } from 'react';
import { Editor } from "react-draft-wysiwyg";
import { ContentState, EditorState } from "draft-js";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import { useHistory, useLocation } from 'react-router';
import { useDispatch } from 'react-redux';
import { updateNote } from '../../Redux/note/noteActions';



const NoteEditor = () => {
 
  const user_id = 3
  const history = useHistory()
  const {state}= useLocation();
  const note = state
  //console.log(state)
  const dispatch = useDispatch()

  const [editorState, setEditorState] = useState(() =>
    EditorState.createWithContent(ContentState.createFromText(note.content))
    //EditorState.createEmpty()
  );

  const handleUpdate = () => {
    //console.log(editorState.getCurrentContent().getPlainText())
    const updatedNote = {
      id : note.id,
      title : note.title,
      content: editorState.getCurrentContent().getPlainText(),
      creator : {
        id: user_id
      },
      updatedDate: new Date()
    }

    dispatch(updateNote({user_id, note: updatedNote}))

    history.push("/notes")
    //console.log(editorState.getCurrentContent().getPlainText())
  }

  return (<div>
    <button onClick={() => handleUpdate()} className='purpleBg font25 radius6 lightColor tag'>Save</button>
    <h2>{note.title}</h2>
    <div style={{ border: "1px solid black", padding: '2px', minHeight: '400px' }}>
      <Editor
        editorState={editorState}
        onEditorStateChange={setEditorState}
      />
    </div>
  </div>
);
  
}

export default NoteEditor;