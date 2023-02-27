import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { addTask } from "../../Redux/task/taskActions";

import FullButton from "../Buttons/FullButton"

const CreateTaskForm = ({close, statusId = 1}) => {
    const [title, setTitle] = useState("");
    const [label, setLabel] = useState("");

    const dispatch = useDispatch();

    const handleSubmit = (e) => {

        e.preventDefault();
        const taskData = {
        project_id : 1,
        task : {
            title,
            label,
            reporter_id : 2,
            statusId
            }  
        }

        dispatch(addTask(taskData));

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
                        <label htmlFor="assignee">Label:</label>
                        <input
                            type=""
                            id="label"
                            value={label}
                            onChange={(event) => setLabel(event.target.value)}
                        />
                    </div>
                    {/* <div>
                        <label htmlFor="task-status-id">Status :</label>
                        <select name="task-status-id" defaultValue={"1"} onChange={(event) => setStatusId(event.target.value)}>
                            <option value="1">To do</option>
                            <option value="2">In progress</option>
                            <option value="3">Done</option>
                        </select>
                    </div> */}
                    <FullButton title="Create Task" />
                </form>
            </div>
        </div>
    );
};

export default CreateTaskForm;
// export default connect(null,{addTaskApi})(CreateTaskForm);
