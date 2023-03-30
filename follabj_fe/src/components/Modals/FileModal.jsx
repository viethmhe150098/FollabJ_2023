import React, { useState } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";

import styled from "styled-components";
import { getFiles, uploadFile } from "../../Redux/file/fileActions";
import { toast } from 'react-toastify';
import FullButton from "../../components/Buttons/FullButton"
const FileModal = ({ type, close }) => {
  const dispatch = useDispatch();
  const [file, setFile] = useState(null);
  const [fileSize, setFileSize] = useState(0);

  const project_id = useSelector((state) => state.project.currentProject.id);
  const user_id = useSelector((state) => state.auth.login.currentUser.id);

  const MAX_FILE_SIZE = 50 * 1024 * 1024; // 50Mb in bytes

  const handleSubmit = (e) => {
    e.preventDefault();
    if (file != null && fileSize <= MAX_FILE_SIZE) {
      const formData = new FormData();
      formData.append("file", file);
      formData.append("u_id", user_id);
      dispatch(uploadFile({ project_id, data: formData }));
      close();
    } else if (file != null && fileSize > MAX_FILE_SIZE) {
      toast.warn("File size should be less than 50Mb!"); // display the toast notification
    } else {
      toast.warn("Please choose a file!"); // display the toast notification
    }
  }

  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    setFile(selectedFile);
    setFileSize(selectedFile.size);
  }

  return (
    <>
      <Modal>
        <a className="close" onClick={close}>
          &times;
        </a>
        {type == "update" && (
          <>
            <h2>File Detail</h2>
            <button onClick={() => { }} className='greenBg font25 radius6 lightColor tag'>Download</button>
          </>
        )}
        {type == null && (<h2>Upload File</h2>)}

        <form id="taskForm" onSubmit={(e) => { handleSubmit(e) }} encType="multipart/form-data">
          <div className="form-group">
            <label htmlFor="title" className="font18">File Upload: </label>
            <input
              type="file"
              id="file"
              onChange={handleFileChange}
            ></input>
            {fileSize > 0 && (
              <p className="font18 extraBold" style={{ marginTop: '30px' }}>File size: {(fileSize / (1024)).toFixed(2)}Kb = {(fileSize / (1024 * 1024)).toFixed(2)}Mb</p>
            )}
          </div>
          {type == null && (<FullButton title="Upload File" ></FullButton>)}
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