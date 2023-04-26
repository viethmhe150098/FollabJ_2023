import React from 'react';
import { useSelector } from 'react-redux';

const Message = ({ message }) => {

  const uid = useSelector((state) => state.auth.login.currentUser.id)

  const messageClass =
    message.uid === uid
      ? inlineStyle.sent
      : inlineStyle.received


  return (
    <div>
      <div style={{ ...inlineStyle.message, ...messageClass }}>
        <p style={inlineStyle.name}>{message.name}</p>
        <p>{message.text}</p>
      </div>
    </div>
  );
};

const inlineStyle = {
  message: {
    display: "flex",
    margin: "1rem",
    paddingTop: "0.5rem", /* 8px */
    paddingBottom: "0.5rem",
    paddingLeft: "0.75rem", /* 12px */
    paddingRight: "0.75rem",
    borderTopLeftRadius: "9999px",
    borderTopRightRadius: "9999px",
  },
  name: {
    position: "relative",
    bottom: "50px",
    color: "#4b5563",
    fontSize: "0.75rem",
    lineHeight: "1rem"
  },
  sent: {
    backgroundColor: "#395dff",
    alignItems: "end",
    color: "white",
    flexDirection: "column-reverse",
    textAlign: "end",
    float: "right",
    borderBottomLeftRadius: "9999px",
  },
  received: {
    backgroundColor: "#e5e5ea",
    color: "black",
    flexDirection: "column-reverse",
    float: "left",
    borderBottomRightRadius: "9999px"
  }
}

export default Message;
