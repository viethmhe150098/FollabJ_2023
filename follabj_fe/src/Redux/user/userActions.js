import { createAsyncThunk } from "@reduxjs/toolkit";
import * as userAPI from "./userAPI";

export const getUsers = createAsyncThunk("FETCH_USERS", async ()=>{
    try {
        const response = await userAPI.fetchUsers()
        return response.data
    } catch (error) {
        throw error
    }
})

export const banUser = createAsyncThunk("BAN_USER", async (user_id) => {
    try {
        const response = await userAPI.updateUserStatus(user_id, 2)
        return user_id
    } catch (error) {
        throw error
    }
})

export const unbanUser = createAsyncThunk("UNBAN_USER", async (user_id) => {
    try {
        const response = await userAPI.updateUserStatus(user_id, 1)
        return user_id
    } catch (error) {
        throw error
    }
})
