import React, { useState } from 'react';
import {db} from '../../../firebase'
import {addDoc, collection, serverTimestamp} from 'firebase/firestore'
import { useSelector } from 'react-redux';

// const style = {
//   form: `h-14 w-full max-w-[728px] flex text-xl fixed bottom-0`,
//   input: `w-full text-xl p-3 bg-gray-900 text-white outline-none border-none`,
//   button: `w-[20%] bg-green-500`,
// };

const InputMessage = ({scroll}) => {
  const [input, setInput] = useState('');

  const project_id = useSelector((state)=> state.project.currentProject.id)

  const uid = useSelector((state)=> state.auth.login.currentUser.id)

  const email = useSelector((state)=> state.auth.login.currentUser.email)

  const sendMessage = async (e) => {
    e.preventDefault()
    if (input === '') {
        alert('Please enter a valid message')
        return
    }

    const username = email.substring(0, email.indexOf("@"));

    await addDoc(collection(db, 'group_'+project_id), {
        text: input,
        name: username,
        uid,
        timestamp: serverTimestamp()
    })
    setInput('')
    scroll.current.scrollIntoView({behavior: 'smooth'})
  }

  return (
    <form onSubmit={sendMessage} style={inlineStyle.form}>
      <input
        value={input}
        onChange={(e) => setInput(e.target.value)}
        style={inlineStyle.input}
        type='text'
        placeholder='Message'
      />
      <button style={inlineStyle.button} type='submit'>
        Send
      </button>
    </form>
  );
};

const inlineStyle = {
  form: {
    height: "3.5rem",
    width: "100%",
    maxWidth: "728px",
    display: "flex",
    fontSize: "1.25rem",
    lineHeight: "1.75rem",
    position: "fixed",
    bottom: "0px"
  },
  input: {
    width: "100%",
    fontSize: "1.25rem",
    lineHeight: "1.75rem",
    margin: "0",
    padding: "0.75rem",
    backgroundColor: "#111827",
    color: "#ffffff",
    outline: "2px solid transparent",
    border: "none"
  },
  button: {
    width: "20%",
    backgroundColor: "#22c55e"
  }
}


export default InputMessage;
