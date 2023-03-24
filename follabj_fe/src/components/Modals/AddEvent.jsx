import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { addEvent, deleteEvent, updateEvent } from "../../Redux/event/eventActions";
import styled from "styled-components";
import "react-datepicker/dist/react-datepicker.css";
import DatePicker from "react-datepicker";
import { useSelector } from "react-redux";



const CreateEventForm = ({ type, close, event }) => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [participantList, setParticipantList] = useState([]);

  const [modalType, setType] = useState(type)

  const user_id = useSelector((state) => state.auth.login.currentUser.id)
  const members = useSelector((state) => state.project.currentProject.members);

  const project_id = useSelector((state) => state.project.currentProject.id);

  const dispatch = useDispatch();

  const userRole = useSelector((state) => state.project.currentProject.userRole);

  useEffect(() => {
    if (type == "readonly") {
      var form = document.getElementById('eventForm');
      var elements = form.elements;
      for (var i = 0, len = elements.length; i < len; ++i) {
        elements[i].readOnly = true;
      }
    }

    if (event != null) {
      setTitle(event.title)
      setDescription(event.describe)
      setStartDate(new Date(event.start))
      setEndDate(new Date(event.end))
      setParticipantList(event.participantList)
    }

  }, [])

  const handleCheckboxChange = (event) => {
    const selectedParticipantId = event.target.value;
    if (event.target.checked) {
      //console.log("checked");
      setParticipantList([...participantList, { id: selectedParticipantId }]);
    } else {
      const filteredParticipantList = participantList.filter(
        (participant) => participant.id != selectedParticipantId ? participant : null
      );
      //console.log("unchecked");
      setParticipantList(filteredParticipantList);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // const startDate = new Date().setDate(2)
    // const endDate = new Date().setDate(7)
    const event = {
      title,
      description,
      startDate,
      endDate,
      project_id,
      participantList
    }
    //console.log(event)
    dispatch(addEvent(event));
    close()
  };
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
  const handleUpdate = () => {
    var form = document.getElementById('eventForm');
    var elements = form.elements;
    for (var i = 0, len = elements.length; i < len; ++i) {
      elements[i].readOnly = false;
    }
    setType("update")

  }

  const handleDelete = () => {
    dispatch(deleteEvent({ project_id, event_id: event.id, title: event.title }))
    close()
  }


  const handleCommitUpdate = () => {

    const updatedEvent = {
      id: event.id,
      title,
      description,
      startDate,
      endDate,
      project_id,
      participantList
    }

    dispatch(updateEvent(updatedEvent))

    close()
  }

  return (
    <Modal>
      <a className="close" onClick={close}>
        &times;
      </a>
      {modalType == "readonly" && (<>
        <h2>View Event</h2>
        {user_id == event.project.leader.id &&
          (
            <div style={{ marginBottom: '20px' }}>
              <span onClick={() => handleUpdate()} className="status" style={makeStyle('Update')}>Update event</span>
              <span onClick={() => handleDelete()} className="status" style={makeStyle('Delete')}>Delete event</span>
            </div>
          )}
      </>)}
      {modalType == "update" && (
          <div style={{ marginBottom: '20px' }}>
            <h2>Update Event</h2>
            <span onClick={() => handleCommitUpdate()} className="status" style={makeStyle('Update')}>Commit Update event</span>
          </div>
      )}
      {modalType == null && (<h2>Create Event</h2>)}
      <div className="form-group">
        <form id="eventForm" onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="title">Event Title</label>
            <textarea
              id="title"
              value={title}
              onChange={(event) => setTitle(event.target.value)}
              required
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
          {modalType != null &&
            <div className="form-group">
              <label >Project Name: {event.project.name}</label>
            </div>
          }
          <div className="form-group">
            <label>Participants: </label>
            {members.map((member) => (
              <div key={member.id}>
                <input disabled={modalType == "readonly"}
                  type="checkbox"
                  id={`participant-${member.id}`}
                  value={member.id}
                  onChange={handleCheckboxChange}
                  // checked={assigneeList.includes(teamMember.id)}
                  checked={participantList.some((participant) => participant.id == member.id)}
                />
                <label htmlFor={`assignee-${member.id}`}>
                  {member.username}
                </label>
              </div>
            ))}
          </div>
          {modalType == null && (<button type="submit">Create Event</button>)}
        </form>
      </div>
    </Modal>
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
