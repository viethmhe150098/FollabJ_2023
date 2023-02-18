import React, { useState } from "react";

import FullButton from "../Buttons/FullButton"

const CreateProject = () => {
    const [prjID, setPrjID] = useState("");
    const [prjName, setPrjName] = useState("");
    const [prjDes, setPrjDes] = useState("");

    const handleSubmit = (event) => {
        event.preventDefault();
        
        
        //Code create Task here
        console.log(prjID, prjName, prjDes);
    };

    return (
        <div className="modal">
            <div className="content">
                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="prj-name">Project Name:</label>
                        <input
                            type="text"
                            id="prjName"
                            value={prjName}
                            onChange={(event) => setPrjName(event.target.value)}
                        />
                    </div>
                    
                    <div>
                        <label htmlFor="description">Project Description:</label>
                        <textarea rows="4"
                            id="prjDes"
                            value={prjDes}
                            onChange={(event) => setPrjDes(event.target.value)}
                        />
                    </div>
                    <FullButton title="Create Project" action={"submit"}/>
                </form>
            </div>
        </div>
    );
};

export default CreateProject;
