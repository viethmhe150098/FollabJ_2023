import { createReducer } from "@reduxjs/toolkit";
import { acceptRequest, declineRequest, getRequests } from "./requestActions";


const initialState = []

export const requestReducer = createReducer(initialState,(builder)=>{
    builder
    .addCase(getRequests.fulfilled, (state,action) => {
        return action.payload.content
    })
    .addCase(acceptRequest.fulfilled, (state,action) => {
        return state.filter((request) => request.id !== action.payload)
    })
    .addCase(declineRequest.fulfilled, (state,action) => {
        return state.filter((request) => request.id !== action.payload)
    })
})