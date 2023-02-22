import React, { useState } from "react";
 import { useDispatch } from "react-redux";
 import { useHistory } from "react-router";
 import { createProject } from "../../Redux/auth/apiRequest";

import FullButton from "../Buttons/FullButton"

const CreateProject = () => {
    const [prjID, setPrjID] = useState("");
    const [prjName, setPrjName] = useState("");
    const [prjDes, setPrjDes] = useState("");

     const dispatch = useDispatch();
     const navigate = useHistory();

    const access_token = localStorage.getItem('access_token');
    const handleSubmit = (event) => {
         event.preventDefault();
         const newProject = {
             prjID: prjID,
             prjName: prjName,
             prjDes: prjDes
         };
         createProject(newProject,access_token,dispatch,navigate);
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
