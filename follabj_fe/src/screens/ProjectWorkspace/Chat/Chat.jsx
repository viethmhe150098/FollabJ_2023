import React, { useState, useEffect, useRef } from 'react';
import Message from './Message.jsx';
import InputMessage from './InputMessage.jsx';
import { db } from '../../../firebase';
import { query, collection, orderBy, onSnapshot } from 'firebase/firestore';
import { useSelector } from 'react-redux';
import styled from "styled-components";
// const style = {
//   main: `flex flex-col p-[50px]`,
// };

const Chat = () => {
  const [messages, setMessages] = useState([]);
  const scroll = useRef();

  const project_id = useSelector((state) => state.project.currentProject.id)

  const HeaderInfo = styled.div`
  margin-bottom: 30px;
  margin-left: 5px;
  @media (max-width: 860px) {
    text-align: center;
  }
`
  useEffect(() => {
    const q = query(collection(db, 'group_' + project_id), orderBy('timestamp'));
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
      <HeaderInfo>
        <h1 className="font30 extraBold">Chat</h1>
      </HeaderInfo>
      <div style={{ maxHeight: '700px', overflow: 'auto' }}>
        <br></br>
        <div style={inlineStyle.main}>
          {messages &&
            messages.map((message) => (
              <Message key={message.id} message={message} />
            ))}
          {/* Send Message Compoenent */}
          <InputMessage scroll={scroll} />
          <span ref={scroll}></span>
        </div>
      </div>
    </>
  );
};

const inlineStyle = {
  main: {
    display: "flex",
    flexDirection: "column",
  }
}

export default Chat;
