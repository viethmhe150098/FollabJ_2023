import axios from "axios";

const projectUrl = (project_id) => "http://localhost:8080/project/"+project_id;

export const fetchProjectsByUserId = (user_id) => axios.get("http://localhost:8080/"+user_id);

