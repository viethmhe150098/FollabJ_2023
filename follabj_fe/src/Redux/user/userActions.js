import { createAsyncThunk } from "@reduxjs/toolkit";
import * as userAPI from "./userAPI";
import { toast } from 'react-toastify';

export const getUsers = createAsyncThunk("FETCH_USERS", async () => {
    try {
        const response = await userAPI.fetchUsers()
        return response.data
    } catch (error) {
        throw error
    }
})

export const banUser = createAsyncThunk("BAN_USER", async (user) => {
    try {
        const response = await userAPI.updateUserStatus(user.id, 2)
        toast.success(`BANNED user ${user.username} sucessfully!`)

        return user.id
    } catch (error) {
        throw error
    }
})

export const unbanUser = createAsyncThunk("UNBAN_USER", async (user) => {
    try {
        const response = await userAPI.updateUserStatus(user.id, 1)
        toast.success(`UNBANNED user ${user.username} sucessfully!`)
        return user.id
    } catch (error) {
        throw error
    }
})
