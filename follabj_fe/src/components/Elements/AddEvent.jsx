import moment from "moment";
import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { addEvent } from "../../Redux/event/eventActions";

import FullButton from "../Buttons/FullButton"

const CreateEventForm = ({close}) => {
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    
    const dispatch = useDispatch();

    const handleSubmit = (e) => {
        e.preventDefault();

        const startDate = new Date().setDate(2)
        const endDate = new Date().setDate(7)
        const event = {
            title,
            description,
            startDate,
            endDate,
            project_id : 1
        }

        dispatch(addEvent(event));
    };

    return (
        <div className="modal">
            <a className="close" onClick={close}>
                &times;
            </a>
            <div className="content">
                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="event-title">Event Title:</label>
                        <input
                            type="text"
                            id="event-title"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                        />
                    </div>
                    <div>
                        <label htmlFor="description">Description :</label>
                        <input
                            type=""
                            id="description"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                        />
                    </div>
                    <FullButton title="Create Event" />
                </form>
            </div>
        </div>
    );
};

export default CreateEventForm;
// export default connect(null,{addTaskApi})(CreateTaskForm);
