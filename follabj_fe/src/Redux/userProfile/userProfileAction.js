import { createAsyncThunk } from "@reduxjs/toolkit";
import * as userProfileApi from "./userProfileApi";

export const getUserProfileByUserId = createAsyncThunk("FETCH_USERPROFILE_BY_USER_ID", async (user_id) =>{
    try {
        const response = await userProfileApi.fetchUserProfileByUserId(user_id)
        //console.log(response)
        return response.data
    } catch (error) {
        console.log(error);
    }
})