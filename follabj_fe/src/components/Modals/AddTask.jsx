import React, { useState } from "react";
import DatePicker from "react-datepicker";
import styled from "styled-components";
import "react-datepicker/dist/react-datepicker.css";
import { addTask } from "../../Redux/task/taskActions";
import { useDispatch } from "react-redux";

const AddTaskModal = ({close, statusId=1,  type}) => {

    const dispatch = useDispatch();

    const teamMembers = [
        { id: 2, name: "Jane Doe" },
        { id: 3, name: "Bob Johnson" },
        { id: 4, name: "Alice Lee" },
        { id: 5, name: "Mike Brown" }
    ];
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [label, setLabel] = useState("")
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [assigneeList, setAssigneeList] = useState([]);

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
          project_id : 1,
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
        
        console.log(taskData)
        dispatch(addTask(taskData));

    };


    return (
        <>
            <Modal>
                <a className="close" onClick={close}>
                    &times;
                </a>
                <h2>Create Task</h2>
                <form onSubmit={handleCreateTask}>
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
                            value={new Date()}
                            showTimeSelect
                            onChange={(date) => setStartDate(date)}
                            dateFormat="dd/MM/yyyy hh:mm a"

                        />
                         <label htmlFor="endDate">End Date</label>
                        <DatePicker
                            id="endDate"
                            selected={endDate}
                            value={new Date()}
                            showTimeSelect
                            onChange={(date) => setEndDate(date)}
                            dateFormat="dd/MM/yyyy hh:mm a"
                        />
                    </div>
                    <div className="form-group">
                        <label>Assignees</label>
                        {teamMembers.map((teamMember) => (
                            <div key={teamMember.id}>
                                <input
                                    type="checkbox"
                                    id={`assignee-${teamMember.id}`}
                                    value={teamMember.id}
                                    onChange={handleCheckboxChange}
                                    // checked={assigneeList.includes(teamMember.id)}
                                    checked = {assigneeList.some((assignee) => assignee.id == teamMember.id)}
                                />
                                <label htmlFor={`assignee-${teamMember.id}`}>
                                    {teamMember.name}
                                </label>
                            </div>
                        ))}
                    </div>
                    <button type="submit">Create Task</button>
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

const BorderDatepicker = styled(DatePicker)`
  display: inline-block;
  border: solid black 1px;
`
export default AddTaskModal;
