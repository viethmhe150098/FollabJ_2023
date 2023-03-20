import axios from "axios";
import React, { useState } from "react";
import { useSelector } from "react-redux";

import styled from "styled-components";


const ChangePasswordModal = ({close}) => {
    
    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");


    const user_id = useSelector((state) => state.auth.login.currentUser.id)

    const handleSubmit = (e) => {
        e.preventDefault()
        const passwordForm = 
            {
                req_u_id: user_id,
                old_password: oldPassword,
                new_password: newPassword
            }
        axios.post("http://localhost:8080/user/password/"+user_id, passwordForm, {
            headers : {
                'Authorization' : "Bearer "+ localStorage.getItem("access_token")
            }
        }).then((response)=>{close()})
    }

    return (
        <>
            <Modal>
                <a className="close" onClick={close}>
                    &times;
                </a>
                <h2>Change Password</h2>
                
                <form id="taskForm" onSubmit={(e)=>{handleSubmit(e)}} encType="multipart/form-data">
                    <div className="form-group">
                        <label htmlFor="oldPassword">Note Title </label>
                        <input
                            type="text"
                            id="oldPassword"
                            onChange={(e) => {setOldPassword(e.target.value)}}
                        ></input>
                    </div>
                    <div className="form-group">
                        <label htmlFor="newPassword">Note Title </label>
                        <input
                            type="text"
                            id="newPassword"
                            onChange={(e) => {setNewPassword(e.target.value)}}
                        ></input>
                    </div>
                    <button onClick={() => handleSubmit()} className='greenBg font25 radius6 lightColor tag'>Change</button>
                </form>
            </Modal>
        </>
    );
}

const Modal = styled.div`

  background-color: #fff;
  padding: 1rem;
  border-radius: 5px;
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);
  height: 100%;
  h2 {
    font-size: 1.5rem;
    margin-bottom: 1.5rem;
  }
  form {
    .form-group {
      margin-bottom: 1rem;
      label {
        font-weight: bold;
        margin-bottom: 0.5rem;
      }
      input,
      textarea {
        padding: 0.5rem;
        border-radius: 5px;
        border: solid black 1px;
        margin: 0;
        &:focus {
          outline: none;
          box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
        }
      .date-picking {
        display: inline-block;
        witdth: 50 %;
        background-color: orange;
        text-color:white
      }
      }
      textarea {
border: 1px black solid;
        resize: none;
      }
      select {
        border: 1px black solid;

        padding: 0.5rem;
        border-radius: 5px;
        border: none;
        margin-bottom: 0.5rem;
        &:focus {
          outline: none;
          box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
        }
      }
      label {
        font-weight: bold;
        margin-bottom: 0.5rem;
      }
      input[type='checkbox'] {
        border: 1px black solid;

        margin-right: 0.5rem;
      }
      label {
      }
      #start-date, #end-date {
        border: 1px black solid;
        width: 40%;
        &:focus {
          outline: none;
          box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
        }
      }
    }
    button[type='submit'] {
      background-color: orange;
      color: #fff;
      padding: 0.5rem 1rem;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      &:hover {
        background-color: #ff9900;
      }
    }
  }
  
`;

export default ChangePasswordModal