import React, { useState } from "react";
import { useEffect } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { toast } from "react-toastify";

import styled from "styled-components";
import instance from "../../Redux/axiosInstance";
import { getRequestByUserId } from "../../Redux/leaderRequest/requestActions";
import FullButton from "../Buttons/FullButton";


const SendLeaderRequestModal = ({close}) => {
    
    const dispatch  = useDispatch();

    const [message, setMessage] = useState("");
    const [request, setRequest] = useState(null);
    const user_id = useSelector((state) => state.auth.login.currentUser.id)

    const [type, setType] = useState("")

    const handleSubmit = (e) => {
      e.preventDefault()

      const leaderRequestDTO = {
        user: {
            id: user_id
        },
        message
      }
  
      instance.post("/user/request", leaderRequestDTO).then((response) => {
          toast.success(response.data);
          fetchUserRequest();
      })
      
    }

    // useEffect(() => {

    // fetchUserRequest()

    // },[])

    const fetchUserRequest = async () => {
        dispatch(getRequestByUserId()).unwrap().then((result) => 
            {
                if (result.content === null) return;
                setRequest(result.content)
                setMessage(result.content.message)
                setType("readonly")
                var form = document.getElementById('form');
                    var elements = form.elements;
                    for (var i = 0, len = elements.length; i < len; ++i) {
                        elements[i].readOnly = true;
                    } 
            }
        )
    }

    return (
        <>
            <Modal>
                <a className="close" onClick={close}>
                    &times;
                </a>
                <h2>Leader Request: </h2>
                <form id="form" onSubmit={(e)=>{handleSubmit(e)}} encType="multipart/form-data">
                    <div className="form-group">
                        <label className="font20" htmlFor="desc">Message To Admin: </label>
                        <textarea
                            id="desc"
                            value={message}
                            onChange={(event) => setMessage(event.target.value)}
                            required
                        ></textarea>
                    </div>
                    {request != null &&
                    <div className="form-group">
                    <label className="font20" htmlFor="desc">Status: {request.status}</label>
                    </div>
                    }
                    {type == "" && (<FullButton type="submit" title="Send Request" />)}
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

export default SendLeaderRequestModal