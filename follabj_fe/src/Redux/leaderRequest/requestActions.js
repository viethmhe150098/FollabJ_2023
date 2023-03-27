import { createAsyncThunk } from "@reduxjs/toolkit";
import * as requestAPI from "./requestAPI";
import { toast } from 'react-toastify';

export const getRequests = createAsyncThunk("FETCH_REQUESTS", async (page_number)=>{
    try {
        const response = await requestAPI.fetchRequest(page_number)
        return response.data
    } catch (error) {
        throw error
    }
})

export const acceptRequest = createAsyncThunk("ACCEPT_REQUEST", async (request) => {
    try {
        const response = await requestAPI.updateStatusRequest(request.id, 1)
        toast.success(`Accepted user ${request.user_fullname} to be a team leader!`)
        return request.id
    } catch (error) {
        throw error
    }
})
