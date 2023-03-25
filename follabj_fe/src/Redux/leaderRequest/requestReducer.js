import { createReducer } from "@reduxjs/toolkit";
import { acceptRequest, getRequests } from "./requestActions";


const initialState = []

export const requestReducer = createReducer(initialState,(builder)=>{
    builder
    .addCase(getRequests.fulfilled, (state,action) => {
        return action.payload.content.content
    })
    .addCase(acceptRequest.fulfilled, (state,action) => {
        return state.filter((request) => request.id != action.payload)
    })
})