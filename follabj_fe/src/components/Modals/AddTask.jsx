import React, { useState } from "react";
import DatePicker from "react-datepicker";
import styled from "styled-components";
import "react-datepicker/dist/react-datepicker.css";


const AddTask = () => {
    const teamMembers = [
        { id: 1, name: "John Smith" },
        { id: 2, name: "Jane Doe" },
        { id: 3, name: "Bob Johnson" },
        { id: 4, name: "Alice Lee" },
        { id: 5, name: "Mike Brown" }
    ];
    const [taskName, setTaskName] = useState("");
    const [taskDescription, setTaskDescription] = useState("");
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [status, setStatus] = useState("Not Started");
    const [assignees, setAssignees] = useState([]);

    const handleCheckboxChange = (event) => {
        const selectedAssigneeId = event.target.value;
        if (event.target.checked) {
            setAssignees([...assignees, selectedAssigneeId]);
        } else {
            const filteredAssignees = assignees.filter(
                (assigneeId) => assigneeId !== selectedAssigneeId
            );
            setAssignees(filteredAssignees);
        }
    };

    const handleCreateTask = (event) => {
        event.preventDefault();
        // call API to create task with the form data
        // reset form data
        setTaskName("");
        setTaskDescription("");
        setStartDate(null);
        setEndDate(null);
        setStatus("Not Started");
        setAssignees([]);
    };

    return (
        <>
            <Modal>
                <h2>Create Task</h2>
                <form onSubmit={handleCreateTask}>
                <div className="form-group">
                        <label htmlFor="task-name">Task Name</label>
                        <textarea
                            id="task-name"
                            value={taskDescription}
                            onChange={(event) => setTaskName(event.target.value)}
                        ></textarea>
                    </div>
                    <div className="form-group">
                        <label htmlFor="task-description">Task Description</label>
                        <textarea
                            id="task-description"
                            value={taskDescription}
                            onChange={(event) => setTaskDescription(event.target.value)}
                        ></textarea>
                    </div>
                    <div className="form-group">
                        <label htmlFor="start-date">Start Date</label>
                        <DatePicker
                            id="start-date"
                            selected={startDate}
                            onChange={(date) => setStartDate(date)}
                            dateFormat="dd/MM/yyyy"
                        />
                         <label htmlFor="end-date">End Date</label>
                        <DatePicker
                            id="end-date"
                            selected={endDate}
                            onChange={(date) => setEndDate(date)}
                            dateFormat="dd/MM/yyyy"
                        />
                    </div>
                
                    <div className="form-group">
                        <label htmlFor="status">Status</label>
                        <select
                            id="status"
                            value={status}
                            onChange={(event) => setStatus(event.target.value)}
                        >
                            <option value="Not Started">Not Started</option>
                            <option value="In Progress">In Progress</option>
                            <option value="Completed">Completed</option>
                        </select>
                        
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
                                    checked={assignees.includes(teamMember.id)}
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
  max-height: %;
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);
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
      input[type='text'],
      textarea {
        padding: 0.5rem;
        border-radius: 5px;
        border: none;
        margin-bottom: 0.5rem;
        &:focus {
          outline: none;
          box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
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

export default AddTask;
