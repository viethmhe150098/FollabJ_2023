import axios from "axios";

const projectUrl = (project_id) => "http://localhost:8080/project/"+project_id;

export const fetchProjectsByUserId = (user_id) => axios.get("http://localhost:8080/"+user_id);

export const fetchProjectMemberByProjectId = (project_id) => axios.get(projectUrl(project_id)+"/members",{
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
})