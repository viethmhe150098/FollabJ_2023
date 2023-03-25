import { createReducer } from "@reduxjs/toolkit";
import { getUserProfileByUserId } from "./userProfileAction";

const initialState = {}; 

export const userProfileReducer = createReducer(initialState,(builder)=>{
    builder
    .addCase(getUserProfileByUserId.fulfilled,(state,action)=>{
        return action.payload
    })
})