import axios from "axios";
import { createProjectFailed, createProjectStart, createProjectSuccess, getProjectFailed, getProjectStart, getProjectSuccess } from "./projectSlice";

const projectUrl =() => "http://localhost:8080/project/";

export const fetchProjectsByUserId = (user_id) => axios.get("http://localhost:8080/"+user_id,{
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});

export const fetchProjectMemberByProjectId = (project_id) => axios.get(projectUrl(project_id)+"/members",{
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
})
