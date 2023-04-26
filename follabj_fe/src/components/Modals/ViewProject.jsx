import React, { useState } from "react";
import { useEffect } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import styled from "styled-components";
import { deactiveProject, deleteProject, getProjectsByUserId, updateProject } from "../../Redux/project/projectActions";
import { LENGTH30, LENGTH100 } from './regexs.js';
import { toast } from "react-toastify";
import { useHistory } from "react-router";
import { setCurrentProjectDescription, setCurrentProjectName } from "../../Redux/project/projectSlice";

const ViewProjectModal = ({ type, close, project }) => {

  const dispatch = useDispatch();
  const history = useHistory();

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
    dispatch(deactiveProject(project_id))
    close()
    if (window.location.pathname == "/aboutProject") 
      history.push("/projects")
  }

  const handleCommitUpdate = (e) => {
    e.preventDefault()
    if (!LENGTH30.test(name)) {
      toast.warn('Project name must not only contain spaces and be up to 30 characters long!')
      return;
    }
    if (!LENGTH100.test(des)) {
      toast.warn('Description must be up to 100 characters long!')
      return;
    }
    const projectData = {
      id: project.id,
      p_name: name.trim(),
      p_des: des.trim()
    }
    dispatch(updateProject({ project_id: project.id, project: projectData })).unwrap().then((result) => {
      dispatch(getProjectsByUserId(user_id))
      dispatch(setCurrentProjectName(result.p_name))
      dispatch(setCurrentProjectDescription(result.p_des))
    })
    close()
  }

  // useEffect(() => {
  //   dispatch(getProjectsByUserId(user_id))
  // }, [])

  useEffect(() => {
    if (type == "readonly") {
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

  }, [])

  console.log(project.members)

  return (
    <>
      <div className="modal">
        <div className="content">
          <form id="projectForm" onSubmit={(e) => { handleCommitUpdate(e) }}  >
            <div>
              <label className="font20" htmlFor="prj-name">Project Name:</label>
              <input
                type="text"
                value={name}
                id="title"
                onChange={(e) => { setName(e.target.value) }}
                required
              ></input>
            </div>

            <div>
              <label className="font20" htmlFor="desc">Project Description: </label>
              <textarea rows="4"
                id="prjDes"
                value={des}
                style={{ padding: '1rem' }}
                onChange={(event) => setDescription(event.target.value)}
              />
            </div>
            <div>
              {(modalType === "update" && project.leader.id === user_id) && (
                <>
                  <UpdateButton className='animate'>Commit Update</UpdateButton>
                  <DeleteButton onClick={() => close()} className='animate'>Cancel Update</DeleteButton>

                </>
              )}
              {(modalType === "readonly" && project.leader.id === user_id) && (<>
                {
                  (<>
                    <UpdateButton className="animate" onClick={() => handleUpdate()}>Update</UpdateButton>
                    <DeleteButton className="animate" onClick={() => handleDelete(project.id)}>Deactive</DeleteButton>
                  </>)}
              </>)}
            </div>
          </form>
        </div>
      </div>

    </>
  );
}
const Button = styled.button`
  display: inline-block;
  min-width: 40%;
  margin: 1%;
  padding: 10px 20px;
  border-radius: 6px;
  font-size: 18px;
  font-weight: 700;
  cursor: pointer;
`;

const UpdateButton = styled(Button)`
position: absolute;
  background: rgb(145 254 159 / 47%);
  color: green;
  left: 35px;
  
 
  &:hover {
    background: green;
    color: white;
  }
`;

const DeleteButton = styled(Button)`
position: absolute;
  background: #ffadad8f;
  color: red;
  right: 35px;
  &:hover {
    background-color: #ff0000;
    color: white;
  }
`;
export default ViewProjectModal