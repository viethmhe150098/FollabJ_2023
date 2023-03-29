import React, { useState } from 'react';
import { db } from '../../../firebase'
import { addDoc, collection, serverTimestamp } from 'firebase/firestore'
import { useSelector } from 'react-redux';
import styled from 'styled-components';
import Picker from 'emoji-picker-react';
import { toast } from "react-toastify";

// const style = {
//   form: `h-14 w-full max-w-[728px] flex text-xl fixed bottom-0`,
//   input: `w-full text-xl p-3 bg-gray-900 text-white outline-none border-none`,
//   button: `w-[20%] bg-green-500`,
// };

const InputMessage = ({ scroll }) => {
  const [input, setInput] = useState('');

  const project_id = useSelector((state) => state.project.currentProject.id)

  const uid = useSelector((state) => state.auth.login.currentUser.id)

  const email = useSelector((state) => state.auth.login.currentUser.email)
  const [showPicker, setShowPicker] = useState(false);
  const onEmojiClick = (emojiObject, event) => {
    console.log(emojiObject.emoji)
    setInput(prevInput => prevInput + emojiObject.emoji);
  };

  const sendMessage = async (e) => {
    e.preventDefault()
    if (input.trim() === '') {
      toast.warn('Empty message!', {
        autoClose: 5000,
      });
      return
    }

    const username = email.substring(0, email.indexOf("@"));
    await addDoc(collection(db, 'group_' + project_id), {
      text: input,
      name: username,
      uid,
      timestamp: serverTimestamp()
    })
    setInput('')
    scroll.current.scrollIntoView({ behavior: 'smooth' })
  }

  return (
    <div style={{
      bottom: "0",
      maxWidth: "1000px"
    }}>

      <Form onSubmit={sendMessage} >
        <Input
          value={input}
          onChange={(e) => setInput(e.target.value)}
          type='text'
          placeholder='Message'
        />
        <div style={{ position: 'absolute', right: '100px', left: '1050px', top: '30px' }}>
          <img
            className="emoji-icon"
            src="https://icons.getbootstrap.com/assets/icons/emoji-smile.svg"
            onClick={() => setShowPicker(val => !val)} />
          <div style={{ position: 'relative', bottom: '500px', right: '200px' }}>
            {showPicker && <Picker
              onEmojiClick={onEmojiClick} />}
          </div>
        </div>

        <Button type='submit'>
          Send
        </Button>
      </Form>
    </div>
  );
};

const Form = styled.form`
  position: fixed;
  bottom: 0;
  width: 100%;
  max-width:1200px;
  display: flex;
  justify-content: space-between;
  background-color: #f1f5f9;
  border-radius: 1.5rem;
  padding: 0.75rem 1rem;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
`;

const Input = styled.input`
  width: 100%;
  background-color: #edf2f7;
  color: #2d3748;
  border: none;
  outline: none;
  padding: 0.5rem 0.75rem;
  border-radius: 0.5rem;
  font-size: 1.125rem;
  
  ::placeholder {
    color: #a0aec0;
  }
`;

const Button = styled.button`
  background-color: #48bb78;
  color: #fff;
  font-weight: 500;
  padding: 0.5rem 1rem;
  border: 5px solid #48bb78;
  border-radius: 0.5rem;
  font-size: 1.125rem;
  margin-left: 0.75rem;
  
  &:hover {
    background-color: #38a169;
    border: 5px solid #38a169;
  }
`;


export default InputMessage;
