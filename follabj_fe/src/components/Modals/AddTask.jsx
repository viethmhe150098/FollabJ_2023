import React, { useEffect, useState } from "react";
import DatePicker from "react-datepicker";
import styled from "styled-components";
import "react-datepicker/dist/react-datepicker.css";
import { addTask, deleteTask, updateTask } from "../../Redux/task/taskActions";
import { useDispatch } from "react-redux";
import moment from "moment";
import { useSelector } from "react-redux";

const AddTaskModal = ({type, close, statusId=1, task}) => {

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

    useEffect(() => {
      if(type=="readonly") {
        var form = document.getElementById('taskForm');
        var elements = form.elements;
        for (var i = 0, len = elements.length; i < len; ++i) {
            elements[i].readOnly = true;
        } 
      }

     if(task != null) {
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
          setAssigneeList([...assigneeList, { id: selectedAssigneeId}]);
        } else {
            const filteredAssigneeList = assigneeList.filter(
              (assignee) => assignee.id != selectedAssigneeId ? assignee : null
            );
            //console.log("unchecked");
            setAssigneeList(filteredAssigneeList);
        }
    };

    const handleCreateTask = (event) => {
        event.preventDefault();
        const taskData = {
          project_id ,
          task : {
            title,
            description,
            label,
            startDate,
            endDate,
            statusId,
            assigneeList
          }
        }
        
        //console.log(taskData)
        dispatch(addTask(taskData));
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
        dispatch(deleteTask({project_id, task_id: task.id}))
        close()
    }

    const handleCommitUpdate = () => {

      const updatedTask = {
          id: task.id,
          title,
          description,
          label,
          startDate,
          endDate,
          statusId,
          assigneeList
      }

      dispatch(updateTask({project_id, task: updatedTask}))
      close()
    }

    return (
        <>
            <Modal>
                <a className="close" onClick={close}>
                    &times;
                </a>
                {modalType=="readonly" && (<>
                  <h2>View Task</h2>
                  { userRole == "LEADER" &&
                  ( <>
                  <button onClick={() => handleUpdate()} className='greenBg font25 radius6 lightColor tag'>Update</button>
                  <button onClick={() => handleDelete()} className='redBg font25 radius6 lightColor tag'>Delete</button>
                  </>)}
                </>)}
                {modalType=="update" && (
                  <>
                  <h2>Update Task</h2>
                  <button onClick={() => handleCommitUpdate()} className='greenBg font25 radius6 lightColor tag'>Commit Update</button>
                  </>
                )}
                {modalType==null && (<h2>Create Task</h2>)}
                
                <form id="taskForm" onSubmit={handleCreateTask}>
                <div className="form-group">
                        <label htmlFor="title">Task Title</label>
                        <textarea
                            id="title"
                            value={title}
                            onChange={(event) => setTitle(event.target.value)}
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
                            disabled={modalType == "readonly"}
                            value={task != null ? moment(task.startDate).format("DD/MM/yyyy hh:mm a") : new Date()}
                            showTimeSelect
                            onChange={(date) => setStartDate(date)}
                            dateFormat="dd/MM/yyyy hh:mm a"
                        />
                         <label htmlFor="endDate">End Date</label>
                        <DatePicker
                            id="endDate"
                            selected={endDate}
                            disabled={modalType == "readonly"}
                            value={task != null ? moment(task.endDate).format("DD/MM/yyyy hh:mm a") : new Date()}
                            showTimeSelect
                            onChange={(date) => setEndDate(date)}
                            dateFormat="dd/MM/yyyy hh:mm a"
                        />
                    </div>
                    <div className="form-group">
                        <label>Assignees</label>
                        {members.map((member) => (
                            <div key={member.id}>
                                <input disabled={modalType=="readonly"}
                                    type="checkbox"
                                    id={`assignee-${member.id}`}
                                    value={member.id}
                                    onChange={handleCheckboxChange}
                                    // checked={assigneeList.includes(teamMember.id)}
                                    checked = {assigneeList.some((assignee) => assignee.id == member.id)}
                                />
                                <label htmlFor={`assignee-${member.id}`}>
                                    {member.username}
                                </label>
                            </div>
                        ))}
                    </div>
                    {/* {modalType=="update" && (<button type="submit">Update Task</button>)} */}
                    {modalType==null && (<button type="submit">Create Task</button>)}
                </form>
            </Modal>
        </>
    );
};
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

export default AddTaskModal;
