import React, { useEffect, useState } from 'react';
import { Editor } from "react-draft-wysiwyg";
import { ContentState, convertFromRaw, convertToRaw, EditorState } from "draft-js";
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

  const [editorState, setEditorState] = useState(() => {
    try {
      return EditorState.createWithContent(convertFromRaw(JSON.parse(note.content)))
    } catch (error) {
      return EditorState.createWithContent(ContentState.createFromText(""))
    }
  });

  const handleUpdate = () => {
    //console.log(editorState.getCurrentContent().getPlainText())
    const updatedNote = {
      id: note.id,
      title: note.title,
      // content: editorState.getCurrentContent().getPlainText(),
      content: JSON.stringify(convertToRaw(editorState.getCurrentContent())),
      creator: {
        id: user_id
      },
      createdDate: new Date(note.createdDate),
    }

    //console.log(convertToRaw(editorState.getCurrentContent()))
    dispatch(updateNote({ user_id, note: updatedNote }))

    history.push("/notes")

    //console.log(convertToRaw(editorState.getCurrentContent()))
    //console.log(updatedNote)
  }

  const handleDelete = () =>{
    dispatch(deleteNote({
      user_id,
      note
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