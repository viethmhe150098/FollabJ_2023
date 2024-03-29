import React, { useState } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router";
import styled from "styled-components";
import { assignLeader, leaveProject, leaveProjectAndAssignNewLeader } from "../../Redux/project/projectActions";


const LeaveGroupModal = ({ close }) => {

  const dispatch = useDispatch();

  const history = useHistory();

  const [assignedLeaderId, setAssignedLeaderId] = useState(null);
  const [radioButtonClicked, setRadioButtonClicked] = useState(false);

  const members = useSelector((state) => state.project.currentProject.members)
  //console.log(members)
  const project_id = useSelector((state) => state.project.currentProject.id);

  const user_id = useSelector((state) => state.auth.login.currentUser.id)

  const handleSubmit = (e) => {
    e.preventDefault()

    if (assignedLeaderId != null) {
      //console.log(assignedLeaderId)
      dispatch(assignLeader({
        project_id,
        new_leader_id: assignedLeaderId
      }))
      dispatch(leaveProject({
        project_id,
        user_id
      }))
      history.push("/projects")
    }

    close();
  }

  return (
    <>
      <Modal>
        <a className="close" onClick={close}>
          &times;
        </a>
        <h2>Assign New Leader: </h2>

        <form id="taskForm" onSubmit={(e) => { handleSubmit(e) }}>
          <div className="form-group">
            {members != null && members.map((member, index) =>
              user_id != member.id &&
              <div key={index}>
                <input
                  type="radio"
                  id={"member" + member.id}
                  name="leader"
                  onChange={(e) => {
                    setRadioButtonClicked(true)
                    setAssignedLeaderId(member.id)
                  }}
                ></input>

                <label className="font20" htmlFor={"member" + member.id}>  {member.username}</label><br />
              </ div>

            )}
          </div>
          {radioButtonClicked && <button type="submit">Assign and Leave Group</button>}
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

export default LeaveGroupModal