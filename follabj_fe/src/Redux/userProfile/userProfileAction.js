import { createAsyncThunk } from "@reduxjs/toolkit";
import * as userProfileApi from "./userProfileApi";
import { toast } from "react-toastify";


export const getUserProfileByUserId = createAsyncThunk("FETCH_USER_PROFILE_BY_USER_ID", async(user_id)=>{
    try {
        const response = await userProfileApi.fetchUserProfileByUserId(user_id)
        return response.data
    } catch (error) {
        throw error
    }
})

export const updateUserProfile = createAsyncThunk("UPDATE_USER_PROFILE_BY_USER_ID",async({user_id,updateDataProfile}) =>{
    try {
        const response = await userProfileApi.updateUserProfile(user_id,updateDataProfile)
        toast.success('Updatedeqweqweqe informations successfully!')
        return response.data
    } catch (error) {
        console.log(error)
        toast.error(error.response.data.message);
    }
})