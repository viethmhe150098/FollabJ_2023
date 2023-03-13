import React, { useState, useEffect, useRef } from 'react';
import Message from './Message.jsx';
import InputMessage from './InputMessage.jsx';
import { db } from '../../../firebase';
import { query, collection, orderBy, onSnapshot } from 'firebase/firestore';
import { useSelector } from 'react-redux';

// const style = {
//   main: `flex flex-col p-[50px]`,
// };

const Chat = () => {
  const [messages, setMessages] = useState([]);
  const scroll = useRef();

  const project_id = useSelector((state)=> state.project.currentProject.id)

  useEffect(() => {
    const q = query(collection(db, 'group_'+project_id), orderBy('timestamp'));
    const unsubscribe = onSnapshot(q, (querySnapshot) => {
      let messages = [];
      querySnapshot.forEach((doc) => {
        messages.push({ ...doc.data(), id: doc.id });
      });
      setMessages(messages);
    });
    return () => unsubscribe();
  }, []);

  return (
    <>
      <div style={inlineStyle.main}>
        {messages &&
          messages.map((message) => (
            <Message key={message.id} message={message} />
          ))}
      </div>
      {/* Send Message Compoenent */}
      <InputMessage scroll={scroll} />
      <span ref={scroll}></span>
    </>
  );
};

const inlineStyle = {
  main: {
    display: "flex",
    flexDirection: "column",
    maxWidth: "728px"
  }
}

export default Chat;
