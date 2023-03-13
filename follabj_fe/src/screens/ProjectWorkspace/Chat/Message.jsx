import React from 'react';
import { useSelector } from 'react-redux';

const style = {
  message: `flex items-center shadow-xl m-4 py-2 px-3 rounded-tl-full rounded-tr-full`,
  name: `absolute mt-[-4rem] text-gray-600 text-xs`,
  sent: `bg-[#395dff] text-white flex-row-reverse text-end float-right rounded-bl-full`,
  received: `bg-[#e5e5ea] text-black float-left rounded-br-full`,
};


const Message = ({ message }) => {
  // const messageClass = 
  // message.uid === auth.currentUser.uid
  // ? `${style.sent}`
  // : `${style.received}`

  const uid = useSelector((state)=> state.auth.login.currentUser.id)

  const messageClass = 
  message.uid == uid
  ? inlineStyle.sent
  : inlineStyle.received


  return (
    <div>
      {/* <div className={`${style.message} ${messageClass}`}>
        <p className={style.name}>{message.name}</p>
        <p>{message.text}</p>
      </div> */}
      <div style={{...inlineStyle.message, ...messageClass}}>
        <p style={inlineStyle.name}>{message.name}</p>
        <p>{message.text}</p>
      </div>
    </div>
  );
};

const inlineStyle = {
  message : {
    display: "flex",
    alignItems: "center",
    margin: "1rem",
    paddingTop: "0.5rem", /* 8px */
    paddingBottom: "0.5rem",
    paddingLeft: "0.75rem", /* 12px */
    paddingRight: "0.75rem",
    borderTopLeftRadius: "9999px",
    borderTopRightRadius: "9999px",
  },
  name : {
    position: "absolute",
    marginBottom: "30px",
    color: "#4b5563",
    fontSize: "0.75rem",
    lineHeight: "1rem"
  },
  sent: {
    backgroundColor: "#395dff",
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
