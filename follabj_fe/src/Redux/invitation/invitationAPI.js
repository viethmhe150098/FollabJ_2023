import instance from "../axiosInstance";

const userInvitationUrl = (user_id) => "/user/"+user_id+"/invitation";
const projectInvitationUrl = (project_id) => "/project/"+project_id+"/invitation";

export const fetchInvitationsByProjectId = (project_id) => instance.get(projectInvitationUrl(project_id));

export const fetchInvitationsByUserId = (user_id) => instance.get(userInvitationUrl(user_id));

export const acceptInvitationsToJoinProject = (user_id, invitation) => instance.post(userInvitationUrl(user_id)+"/accept",invitation)