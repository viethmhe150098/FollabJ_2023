import { createAsyncThunk } from "@reduxjs/toolkit";
import * as requestAPI from "./requestAPI";

export const getRequests = createAsyncThunk("FETCH_REQUESTS", async (page_number)=>{
    try {
        const response = await requestAPI.fetchRequest(page_number)
        return response.data
    } catch (error) {
        throw error
    }
})

export const acceptRequest = createAsyncThunk("ACCEPT_REQUEST", async (request_id) => {
    try {
        const response = await requestAPI.updateStatusRequest(request_id, 1)
        return request_id
    } catch (error) {
        throw error
    }
})
