import moment from "moment";
import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { useParams } from "react-router";
import { addEvent } from "../../Redux/event/eventActions";
import styled from "styled-components";
import "react-datepicker/dist/react-datepicker.css";
import DatePicker from "react-datepicker";

const CreateEventForm = ({close}) => {
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [participantList, setParticipantList] = useState([]);

    //let { project_id } = useParams();
    const project_id = 1;

    const dispatch = useDispatch();

    const handleSubmit = (e) => {
        e.preventDefault();

        // const startDate = new Date().setDate(2)
        // const endDate = new Date().setDate(7)
        const event = {
            title,
            description,
            startDate,
            endDate,
            project_id
        }
        //console.log(event)
        dispatch(addEvent(event));
    };

    return (
        <Modal>
            <a className="close" onClick={close}>
                &times;
            </a>
            <div className="form-group">
                <form onSubmit={handleSubmit}>
                <div className="form-group">
                        <label htmlFor="title">Event Title</label>
                        <textarea
                            id="title"
                            value={title}
                            onChange={(event) => setTitle(event.target.value)}
                        ></textarea>
                    </div>
                    <div className="form-group">
                        <label htmlFor="description">Event Description</label>
                        <textarea
                            id="description"
                            value={description}
                            onChange={(event) => setDescription(event.target.value)}
                        ></textarea>
                    </div>
                    <div className="form-group">
                        <span></span>
                        <label htmlFor="start-date">Start Date</label>
                        <DatePicker
                            id="start-date"
                            selected={startDate}
                            onChange={(date) => setStartDate(date)}
                            dateFormat="dd/MM/yyyy hh:mm a"
                            showTimeSelect
                        />
                        <label htmlFor="end-date">End Date</label>
                        <DatePicker
                            id="end-date"
                            selected={endDate}
                            onChange={(date) => setEndDate(date)}
                            dateFormat="dd/MM/yyyy hh:mm a"
                            showTimeSelect
                        />
                    </div>
                    <button type="submit">Create Event</button>
                </form>
            </div>
        </Modal>
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

export default CreateEventForm;
// export default connect(null,{addTaskApi})(CreateTaskForm);
