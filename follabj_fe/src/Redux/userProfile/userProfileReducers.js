import { createReducer } from "@reduxjs/toolkit";
import { getUserProfileByUserId, updateUserProfile } from "./userProfileAction";

const initialState = {}; 

export const userProfileReducer = createReducer(initialState,(builder)=>{
    builder
    .addCase(getUserProfileByUserId.fulfilled,(state,action)=>{
        return action.payload
    })
    .addCase(updateUserProfile.fulfilled,(state,action)=>{
        console.log(action.payload);
    })
})