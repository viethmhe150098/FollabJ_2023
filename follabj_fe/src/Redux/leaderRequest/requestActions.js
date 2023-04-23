import { createAsyncThunk } from "@reduxjs/toolkit";
import * as requestAPI from "./requestAPI";
import { toast } from 'react-toastify';

export const getRequests = createAsyncThunk("FETCH_REQUESTS", async (page_number)=>{
    try {
        const response = await requestAPI.fetchRequest()
        return response.data
    } catch (error) {
        throw error
    }
})

export const getRequestByUserId = createAsyncThunk("GET_REQUEST_BY_USER_ID", async ()=>{
    try {
        const response = await requestAPI.getRequestByUserId()
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

export const declineRequest = createAsyncThunk("DECLINE_REQUEST", async (request) => {
    try {
        const response = await requestAPI.updateStatusRequest(request.id, 2)
        toast.success(`Decline user ${request.user.email} to be a team leader!`)
        return request.id
    } catch (error) {
        throw error
    }
})
