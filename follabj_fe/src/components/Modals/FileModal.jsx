import React, { useState } from "react";
import { useDispatch } from "react-redux";

import styled from "styled-components";
import { uploadFile } from "../../Redux/file/fileActions";
import FullButton from "../Buttons/FullButton"


const FileModal = ({type, close}) => {
    const dispatch  = useDispatch();
    const [file, setFile] = useState(null);

    const project_id = 1;

    const handleSubmit = (e) => {
      const formData = new FormData()
      formData.append("file", file)
      formData.append("u_id", 3)
      e.preventDefault();
      if (file != null ) {
          //console.log(file);
          dispatch(uploadFile({project_id, data: formData}));
          
      } else {
          alert("please choose file")
      }
  }

    return (
        <>
            <Modal>
                <a className="close" onClick={close}>
                    &times;
                </a>
                {type=="update" && (
                  <>
                  <h2>File Detail</h2>
                  <button onClick={()=> {}} className='greenBg font25 radius6 lightColor tag'>Download</button>
                  </>
                )}
                {type==null && (<h2>Upload File</h2>)}
                
                <form id="taskForm" onSubmit={(e)=>{handleSubmit(e)}} encType="multipart/form-data">
                <   div className="form-group">
                        <label htmlFor="title">File Upload: </label>
                        <input
                            type="file"
                            id="file"
                            onChange={(e) => {setFile(e.target.files[0])}}
                        ></input>
                    </div>
                    {/* {modalType=="update" && (<button type="submit">Update Task</button>)} */}
                    {type==null && (<button type="submit">Upload File</button>)}
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

export default FileModal