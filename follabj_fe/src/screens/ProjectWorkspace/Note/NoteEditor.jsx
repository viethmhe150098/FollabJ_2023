import React, { useEffect, useState } from 'react';
import { Editor } from "react-draft-wysiwyg";
import { ContentState, convertToRaw, EditorState } from "draft-js";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import { useHistory, useLocation } from 'react-router';
import { useDispatch } from 'react-redux';
import { getNotesByUserId, updateNote, deleteNote } from '../../../Redux/note/noteActions';
import { useSelector } from 'react-redux';
import FullButton from "../../../components/Buttons/FullButton";



const NoteEditor = () => {

  const user_id = useSelector((state) => state.auth.login.currentUser.id)
  const history = useHistory()
  const { state } = useLocation();
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
      id: note.id,
      title: note.title,
      content: editorState.getCurrentContent().getPlainText(),
      creator: {
        id: user_id
      },
      createdDate : new Date(note.createdDate),
      updatedDate: new Date()
    }

    dispatch(updateNote({ user_id, note: updatedNote }))

    history.push("/notes")

    //console.log(convertToRaw(editorState.getCurrentContent()))
    //console.log(updatedNote)
  }
  

  const handleDelete = () =>{
    dispatch(deleteNote({
      user_id,
      note_id: note.id
    }))
    history.push("/notes")
  }




  return (<div>
    <div style={{ width: '100px', marginBottom:'40px' }}> <FullButton title={"Save Note"} action={() => handleUpdate()} /></div>
    <div style={{ width: '100px', marginBottom:'40px' }}> <FullButton title={"Delete Note"} action={() => handleDelete()} /></div>
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