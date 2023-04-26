import { createReducer } from "@reduxjs/toolkit/dist";
import { inviteMember } from "../project/projectActions";
import { acceptInvitation, getInvitationsByProjectId, getInvitationsByUserId, rejectInvitation } from "./invitationActions";

const initialState = {
    user_invitations: [],
    project_invitations: []
}

export const invitationReducer = createReducer(initialState, (builder) => {
    builder
        .addCase(getInvitationsByProjectId.fulfilled, (state, action) => {
            state.project_invitations = action.payload
        })
        .addCase(getInvitationsByUserId.fulfilled, (state, action) => {
            state.user_invitations = action.payload
        })
        .addCase(inviteMember.fulfilled, (state, action) => {
            if(action.payload.message==="success")
            state.project_invitations.push(action.payload.content)
        })
        .addCase(acceptInvitation.fulfilled, (state, action) => {
            state.user_invitations = state.user_invitations.filter((invitation) => invitation.id !== action.payload.invitation_id)
        })
        .addCase(rejectInvitation.fulfilled, (state, action) => {
            state.user_invitations = state.user_invitations.filter((invitation) => invitation.id !== action.payload.id)
        })
        
})
