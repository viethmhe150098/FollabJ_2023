import React, { useEffect, useState } from "react";
import DatePicker from "react-datepicker";
import styled from "styled-components";
import "react-datepicker/dist/react-datepicker.css";
import { addTask, deleteTask, updateTask } from "../../Redux/task/taskActions";
import { useDispatch } from "react-redux";
import moment from "moment";
import { LENGTH30, LENGTH50, LENGTH100 } from './regexs.js';
import { useSelector } from "react-redux";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
const AddTaskModal = ({ type, close, statusId = 1, task, columnPosition}) => {

  const dispatch = useDispatch();

  const user_id = useSelector((state) => state.auth.login.currentUser.id)
  const members = useSelector((state) => state.project.currentProject.members);

  const project_id = useSelector((state) => state.project.currentProject.id);

  const userRole = useSelector((state) => state.project.currentProject.userRole);

  //console.log(user_id == members[0].id)

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [label, setLabel] = useState("")
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [assigneeList, setAssigneeList] = useState([]);

  const [modalType, setType] = useState(type)
  const makeStyle = (status) => {
    if (status === 'Update') {
      return {
        background: 'rgb(145 254 159 / 47%)',
        color: 'green',
        marginRight: '20px',
        cursor: 'pointer'
      }
    }
    else if (status === 'Delete') {
      return {
        background: '#ffadad8f',
        color: 'red',
        cursor: 'pointer'
      }
    }
  }
  useEffect(() => {
    if (type === "readonly") {
      var form = document.getElementById('taskForm');
      var elements = form.elements;
      for (var i = 0, len = elements.length; i < len; ++i) {
        elements[i].readOnly = true;
      }
    }

    if (task != null) {
      setTitle(task.title)
      setDescription(task.description)
      setLabel(task.label)
      setStartDate(new Date(task.startDate))
      setEndDate(new Date(task.endDate))
      setAssigneeList(task.assigneeList)
    }

  }, [])

  const handleCheckboxChange = (event) => {
    const selectedAssigneeId = event.target.value;
    if (event.target.checked) {
      //console.log("checked");
      setAssigneeList([...assigneeList, { id: selectedAssigneeId }]);
    } else {
      const filteredAssigneeList = assigneeList.filter(
        (assignee) => assignee.id !== selectedAssigneeId ? assignee : null
      );
      //console.log("unchecked");
      setAssigneeList(filteredAssigneeList);
    }
    //console.log(assigneeList)
  };

  const handleCreateTask = (event) => {
    event.preventDefault();

    if (!LENGTH30.test(title)) {
      toast.warn('Title must not only contain spaces and be up to 30 characters long!')
      return;
    }
    if (!LENGTH100.test(description)) {
      toast.warn('Description must be up to 100 characters long!')
      return;
    }
    if (!LENGTH50.test(label)) {
      toast.warn('Label must be up to 50 characters long!')
      return;
    }
    if (startDate > endDate) {
      toast.warn("Start date cannot be after finish date", {
      });
      return;
    }

    const taskData = {
      project_id,
      task: {
        title: title.trim(),
        description: description.trim(),
        label: label.trim(),
        startDate,
        endDate,
        statusId,
        columnPosition,
        assigneeList
      }
    }
    //console.log(taskData)
    dispatch(addTask(taskData));
    close()
  };

  const handleUpdate = () => {
    var form = document.getElementById('taskForm');
    var elements = form.elements;
    for (var i = 0, len = elements.length; i < len; ++i) {
      elements[i].readOnly = false;
    }
    setType("update")
  }

  const handleDelete = () => {
    dispatch(deleteTask({ project_id, task_id: task.id, task_name: task.title }))
    close()
  }

  const handleCommitUpdate = () => {
    if (!LENGTH30.test(title)) {
      toast.warn('Title must not only contain spaces and be up to 30 characters long!')
      return;
    }
    if (!LENGTH100.test(description)) {
      toast.warn('Description must be up to 100 characters long!')
      return;
    }
    if (!LENGTH50.test(label)) {
      toast.warn('Label must be up to 50 characters long!')
      return;
    }
    if (startDate > endDate) {
      toast.warn("Start date cannot be after finish date", {
      });
      return;
    }
    const updatedTask = {
      id: task.id,
      title: title.trim(),
      description: description.trim(),
      label: label.trim(),
      startDate,
      endDate,
      statusId,
      columnPosition,
      assigneeList
    }

    dispatch(updateTask({ project_id, task: updatedTask }))
    close()
  }

  return (
    <>
      <Modal>
        {/* <a className="close" onClick={close}>
          &times;
        </a> */}
        {modalType === "readonly" && (<>
          <h2>View Task</h2>
          {userRole === "LEADER" &&
            (<div style={{ marginBottom: '20px' }}>
              <span onClick={() => handleUpdate()} className="status" style={makeStyle('Update')}>Update task</span>
              <span onClick={() => handleDelete()} className="status" style={makeStyle('Delete')}>Delete task</span>
            </div>)}
        </>)}
        {modalType === "update" && (
          <div style={{ marginBottom: '20px' }}>
            <h2>Update Task</h2>
            <button onClick={() => handleCommitUpdate()} className="status" style={makeStyle('Update')}>Commit Update</button>
          </div>
        )}
        {modalType == null && (<h2>Create Task</h2>)}

        <form id="taskForm" onSubmit={handleCreateTask}>
          <div className="form-group">
            <label htmlFor="title">Task Title</label>
            <textarea
              id="title"
              value={title}
              onChange={(event) => setTitle(event.target.value)}
              required
            ></textarea>
          </div>
          <div className="form-group">
            <label htmlFor="description">Task Description</label>
            <textarea
              id="description"
              value={description}
              onChange={(event) => setDescription(event.target.value)}
            ></textarea>
          </div>
          <div className="form-group">
            <label htmlFor="label">Task Label</label>
            <textarea
              id="label"
              value={label}
              onChange={(event) => setLabel(event.target.value)}
            ></textarea>
          </div>
          <div className="form-group">
            <label htmlFor="startDate">Start Date</label>
            <DatePicker
              id="startDate"
              selected={startDate}
              disabled={modalType === "readonly"}
              showTimeSelect
              onChange={(date) => setStartDate(date)}
              dateFormat="dd/MM/yyyy hh:mm a"
              required
            />
            <label htmlFor="endDate">End Date</label>
            <DatePicker
              id="endDate"
              selected={endDate}
              disabled={modalType === "readonly"}
              // value={task != null ? moment(task.endDate).format("DD/MM/yyyy hh:mm a") : new Date()}
              showTimeSelect
              onChange={(date) => setEndDate(date)}
              dateFormat="dd/MM/yyyy hh:mm a"
              required
            />
          </div>
          <div className="form-group">
            <label>Assignees</label>
            {members.map((member) => (
              <div key={member.id}>
                <input disabled={modalType === "readonly"}
                  type="checkbox"
                  id={`assignee-${member.id}`}
                  value={member.id}
                  onChange={(e) => {handleCheckboxChange(e)}}
                  // checked={assigneeList.includes(teamMember.id)}
                  checked={assigneeList.some((assignee) => parseInt(assignee.id) === member.id)}
                />
                <label htmlFor={`assignee-${member.id}`}>
                  {member.username}
                </label>
              </div>
            ))}
          </div>
          {/* {modalType=="update" && (<button type="submit">Update Task</button>)} */}
          {modalType == null && (<button type="submit">Create Task</button>)}
        </form>
      </Modal>
    </>
  );
};
const Modal = styled.div`

  background-color: #fff;
  padding: 1rem;
  border-radius: 5px;
  max-height: 100%;
  max-width: 100%;
  min-width: 500px;
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);
  height: 100%;
  max-height: 100%;
  max-width: 100%;
  min-width: 500px;
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

export default AddTaskModal;
