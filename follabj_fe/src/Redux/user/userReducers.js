import { createReducer } from "@reduxjs/toolkit";
import { banUser, getUsers, unbanUser } from "./userActions";


const initialState = []

export const userReducer = createReducer(initialState,(builder)=>{
    builder
    .addCase(getUsers.fulfilled, (state,action) => {
        return action.payload
    })
    .addCase(banUser.fulfilled, (state,action) => {
        return state.map((user) => user.id == action.payload ? {...user, status:2} : user)
    })
    .addCase(unbanUser.fulfilled, (state,action) => {
        return state.map((user) => user.id == action.payload ? {...user, status:1} : user)
    })
})