import React, { useState } from "react";

import FullButton from "../Buttons/FullButton"
import { connect } from 'react-redux'
// import { addTaskApi } from "../../Redux/actions";

const CreateTaskForm = ({addTaskApi, close}) => {
    const [title, setTitle] = useState("");
    const [assignee, setAssignee] = useState("");
    const [statusId, setStatusId] = useState(1);

    const handleSubmit = (e) => {
        e.preventDefault();
        const taskData = {
            title,
            assignee
        }
        addTaskApi({taskData, statusId})

        //Code create Task here
        //console.log(taskId, taskName, assignee);


    };

    return (
        <div className="modal">
            <a className="close" onClick={close}>
                &times;
            </a>
            <div className="content">
                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="task-name">Task Title:</label>
                        <input
                            type="text"
                            id="task-title"
                            value={title}
                            onChange={(event) => setTitle(event.target.value)}
                        />
                    </div>
                    <div>
                        <label htmlFor="assignee">Assignee:</label>
                        <input
                            type=""
                            id="assignee"
                            value={assignee}
                            onChange={(event) => setAssignee(event.target.value)}
                        />
                    </div>
                    <div>
                        <label htmlFor="task-status-id">Task ID:</label>
                        <select name="task-status-id" defaultValue={"1"} onChange={(event) => setStatusId(event.target.value)}>
                            <option value="1">To do</option>
                            <option value="2">In progress</option>
                            <option value="3">Done</option>
                        </select>
                    </div>
                    <FullButton title="Create Task" />
                </form>
            </div>
        </div>
    );
};

export default CreateTaskForm;
// export default connect(null,{addTaskApi})(CreateTaskForm);
