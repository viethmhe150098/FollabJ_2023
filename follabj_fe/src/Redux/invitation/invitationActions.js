import { createAsyncThunk } from "@reduxjs/toolkit/dist"
import * as invitationAPI from "./invitationAPI";

export const getInvitationsByProjectId = createAsyncThunk("FETCH_INVITATIONS_BY_PRJ_ID", async (project_id) => {
    try {
        const response = await invitationAPI.fetchInvitationsByProjectId(project_id)
        //console.log(response)
        return response.data
    } catch (error) {
        console.log(error);
        // throw error
    }
})

export const getInvitationsByUserId = createAsyncThunk("FETCH_INVITATIONS_BY_USER_ID", async (user_id) => {
    try {
        const response = await invitationAPI.fetchInvitationsByUserId(user_id)
        //console.log(response)
        return response.data
    } catch (error) {
        console.log(error);
        // throw error
    }
})

export const acceptInvitation = createAsyncThunk("ACCEPT_INVITATION", async ({user_id, invitation}) => {
    try {
        const response = await invitationAPI.acceptInvitationsToJoinProject(user_id, invitation)
        const joinedProject = response.data
        return {joinedProject, invitation_id : invitation.id}
    } catch (error) {
        console.log(error);
    }
})

export const rejectInvitation = createAsyncThunk("REJECT_INVITATION", async ({user_id, invitation}) => {
    try {
        const response = await invitationAPI.rejectInvitaion(user_id, invitation.id)

        return invitation
    } catch (error) {
        console.log(error);
    }
})