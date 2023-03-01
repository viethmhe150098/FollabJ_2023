import axios from "axios";
import { createProjectFailed, createProjectStart, createProjectSuccess, getProjectFailed, getProjectStart, getProjectSuccess } from "./projectSlice";

const projectUrl =() => "http://localhost:8080/project/";

// export const fetchProjectsByUserId = (user_id) => axios.get("http://localhost:8080/"+user_id);

export const createProject = async(project,access_token,dispatch,navigate) =>{
    dispatch(createProjectStart());

    const role_name = localStorage.getItem("role_name");
    if (role_name.includes('LEADER')) {
        try {
            const res = await axios.post("http://localhost:8080/createproject",
                                            project,
                                            {
                                                headers: {
                                                    Authorization: `Bearer ${access_token}`
                                                }
                                            });
             dispatch(createProjectSuccess(res.data));
             navigate.push("/aboutProject");
         } catch (error) {
            dispatch(createProjectFailed());
         }
    }
}    

export const getAllProjectsByUserId = async(u_id,access_token,dispatch) =>{
    dispatch(getProjectStart());

    const role_name = localStorage.getItem("role_name");
    if (role_name.includes('ACTIVE_USER')) {
        try {
            const res = await axios.post("http://localhost:8080/${u_id}",
                                            {
                                                headers: {
                                                    Authorization: `Bearer ${access_token}`
                                                }
                                            });
        dispatch(getProjectSuccess  ());
        return res.data.map((project) => ({
            p_name: project.p_name,
            p_des: project.p_des,
        }));
         } catch (error) {
            dispatch(getProjectFailed());
         }
    }
}    