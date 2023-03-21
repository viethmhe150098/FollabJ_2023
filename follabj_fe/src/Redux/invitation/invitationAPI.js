import axios from "axios";

const userInvitationUrl = (user_id) => "http://localhost:8080/user/"+user_id+"/invitation";
const projectInvitationUrl = (project_id) => "http://localhost:8080/project/"+project_id+"/invitation";

export const fetchInvitationsByProjectId = (project_id) => axios.get(projectInvitationUrl(project_id),{
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});

export const addInvitation = (project_id,email) => axios.post(projectInvitationUrl(project_id)+"/invite",
    {email},
    {
    headers : {
        'Content-Type': 'application/x-www-form-urlencoded',
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});

export const fetchInvitationsByUserId = (user_id) => axios.get(userInvitationUrl(user_id),{
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});

export const acceptInvitationsToJoinProject = (user_id, invitation) => axios.post(userInvitationUrl(user_id)+"/accept",invitation, {
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
})