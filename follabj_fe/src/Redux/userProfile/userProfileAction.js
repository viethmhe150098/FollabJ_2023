import { createAsyncThunk } from "@reduxjs/toolkit";
import * as userProfileApi from "./userProfileApi";

export const getUserProfileByUserId = createAsyncThunk("FETCH_USER_PROFILE_BY_USER_ID", async(user_id)=>{
    try {
        const response = await userProfileApi.fetchUserProfileByUserId(user_id)
        return response.data
    } catch (error) {
        throw error
    }
})