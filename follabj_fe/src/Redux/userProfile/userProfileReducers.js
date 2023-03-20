import { createReducer } from "@reduxjs/toolkit";
import {getUserProfileByUserId} from "./userProfileAction";

const initialState= [] 
export const userProfileReducer = createReducer(initialState,(builder)=>{
    builder
    .addCase(getUserProfileByUserId.fulfilled, (state, action) => {
        const newState = []
        action.payload.map((userProfile) => {
            const converted = {
                email: userProfile.email,
                username: userProfile.username,
            }
            newState.push(converted)
        })
        return newState
    })
})