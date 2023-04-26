import React, { useState } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router";
import { createProject } from "../../Redux/auth/apiRequest";
import { LENGTH30, LENGTH100 } from './regexs.js';
import { toast } from "react-toastify";
import FullButton from "../Buttons/FullButton"

const CreateProject = ({ close }) => {
    const [prjID, setPrjID] = useState("");
    const [prjName, setPrjName] = useState("");
    const [prjDes, setPrjDes] = useState("");

    const dispatch = useDispatch();
    const navigate = useHistory();

    const user_id = useSelector((state) => state.auth.login.currentUser.id)


    const access_token = localStorage.getItem('access_token');
    const handleSubmit = (event) => {
        event.preventDefault();
        if (!LENGTH30.test(prjName)) {
            toast.warn('Project name must not only contain spaces and be up to 30 characters long!')
            return;
        }
        if (!LENGTH100.test(prjDes)) {
            toast.warn('Description must be up to 100 characters long!')
            return;
        }
        const newProject = {
            id: user_id,
            p_name: prjName.trim(),
            p_des: prjDes.trim()
        };
        createProject(newProject, access_token, dispatch, navigate);
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
                            required />
                    </div>

                    <div>
                        <label htmlFor="description">Project Description:</label>
                        <textarea rows="4"
                            id="prjDes"
                            value={prjDes}
                            style={{ padding: '1rem' }}
                            onChange={(event) => setPrjDes(event.target.value)}
                        />
                    </div>
                    <FullButton title="Create Project" type="submit" />
                </form>
            </div>
        </div>
    );
};

export default CreateProject;
