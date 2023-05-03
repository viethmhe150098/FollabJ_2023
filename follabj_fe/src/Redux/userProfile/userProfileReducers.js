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
        if(action.payload.status === "200")
            toast.success(action.payload.message)
        else 
            toast.error(action.payload.message)
    })
})