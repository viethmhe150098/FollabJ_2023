import instance from "../axiosInstance";

const projectUrl =(project_id) => "/project/"+project_id;

export const fetchProjectsByUserId = (user_id) => instance.get("/"+user_id);

export const fetchProjectMemberByProjectId = (project_id) => instance.get(projectUrl(project_id)+"/members")
