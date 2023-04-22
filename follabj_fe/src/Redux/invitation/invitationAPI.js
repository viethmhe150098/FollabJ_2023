import instance from "../axiosInstance";

const userInvitationUrl = () => "/invitation";
const projectInvitationUrl = (project_id) => "/project/"+project_id+"/invitation";

export const fetchInvitationsByProjectId = (project_id) => instance.get(projectInvitationUrl(project_id));

export const fetchInvitationsByUserId = () => instance.get(userInvitationUrl());

export const acceptInvitationsToJoinProject = (invitation) => instance.post(userInvitationUrl()+"/accept",invitation)

export const rejectInvitaion = (invitation_id) => instance.delete(userInvitationUrl()+"/"+invitation_id)