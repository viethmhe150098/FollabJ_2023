import { createReducer } from "@reduxjs/toolkit";
import { getUserProfileByUserId, updateUserProfile } from "./userProfileAction";
import { toast } from "react-toastify";

const initialState = {}; 

export const userProfileReducer = createReducer(initialState,(builder)=>{
    builder
    .addCase(getUserProfileByUserId.fulfilled,(state,action)=>{
        return action.payload
    })
    .addCase(updateUserProfile.fulfilled,(state,action)=>{
        toast.success(action.payload.message)
    })
})