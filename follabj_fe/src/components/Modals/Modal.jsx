import React, { useState } from "react";

import FullButton from "../Buttons/FullButton"

const CreateTaskForm = ({close}) => {
    const [taskId, setTaskId] = useState("");
    const [taskName, setTaskName] = useState("");
    const [assignee, setAssignee] = useState("");

    const handleSubmit = (event) => {
        event.preventDefault();
        
        
        //Code create Task here
        console.log(taskId, taskName, assignee);
    };

    return (
        <div className="modal">
          
            <div className="content">
                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="task-id">Task ID:</label>
                        <input
                            type="text"
                            id="task-id"
                            value={taskId}
                            onChange={(event) => setTaskId(event.target.value)}
                        />
                    </div>
                    <div>
                        <label htmlFor="task-name">Task Name:</label>
                        <input
                            type="text"
                            id="task-name"
                            value={taskName}
                            onChange={(event) => setTaskName(event.target.value)}
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
                    <FullButton title="Create Task" action={"submit"}/>
                </form>
            </div>
        </div>
    );
};

export default CreateTaskForm;
