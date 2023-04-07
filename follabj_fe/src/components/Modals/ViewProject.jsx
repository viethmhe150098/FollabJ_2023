import React, { useState } from "react";
import { useEffect } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";

import styled from "styled-components";
import { addNote } from "../../Redux/note/noteActions";
import { deleteProject, getProjectsByUserId, updateProject } from "../../Redux/project/projectActions";


const ViewProjectModal = ({type, close, project}) => {
    
    const dispatch  = useDispatch();
    const [name, setName] = useState("");
    const [des, setDescription] = useState("")
    const user_id = useSelector((state) => state.auth.login.currentUser.id)

    const [modalType, setType] = useState(type)

    const handleSubmit = (e) => {
      e.preventDefault()
      
    }

    const handleUpdate = () => {
      var form = document.getElementById('projectForm');
      var elements = form.elements;
      for (var i = 0, len = elements.length; i < len; ++i) {
          elements[i].readOnly = false;
      }
      setType("update")
    }  
    
    const handleDelete = (project_id) => {
      dispatch(deleteProject(project_id))
      close()
    }

    const handleCommitUpdate = () => {
      const projectData = {
        id: project.id,
        p_name: name,
        p_des: des
      }

      dispatch(updateProject({project_id: project.id, project: projectData}))

      close()
    }

    useEffect(() => {
      dispatch(getProjectsByUserId(user_id))
    }, [updateProject])

    useEffect(() => {
        if(type=="readonly") {
          var form = document.getElementById('projectForm');
          var elements = form.elements;
          for (var i = 0, len = elements.length; i < len; ++i) {
              elements[i].readOnly = true;
          } 
       }

       if (project != null) {
        setName(project.name)
        setDescription(project.des)
       }

    },[])

    return (
        <>
            <Modal>
                <a className="close" onClick={close}>
                    &times;
                </a>
                <h2>View Project</h2>
                <form id="projectForm" onSubmit={(e)=>{handleSubmit(e)}} encType="multipart/form-data">
                    <div className="form-group">
                        <label className="font20" htmlFor="title">Project Name: </label>
                        <input
                            type="text"
                            value={name}
                            id="title"
                            onChange={(e) => {setName(e.target.value)}}
                        ></input>
                    </div>
                    <div className="form-group">
                        <label className="font20" htmlFor="desc">Project Description: </label>
                        <textarea
                            id="desc"
                            value={des}
                            onChange={(event) => setDescription(event.target.value)}
                            required
                        ></textarea>
                    </div>
                </form>
                {modalType==="update" && project.members[0].id === user_id && (
                      <>
                      <h2>Update Event</h2>
                      <button onClick={() => handleCommitUpdate()} className='greenBg font25 radius6 lightColor tag'>Commit Update</button>
                      </>
                    )}
                {modalType === "readonly"  && project.members[0].id === user_id && (<>
                        { 
                        ( <>
                        <button onClick={() => handleUpdate()} className='greenBg font25 radius6 lightColor tag'>Update</button>
                        <button onClick={() => handleDelete(project.id)} className='redBg font25 radius6 lightColor tag'>Delete</button>
                        </>)}
                </>)}
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

export default ViewProjectModal