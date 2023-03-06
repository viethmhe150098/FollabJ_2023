import { createAsyncThunk } from "@reduxjs/toolkit";
import * as projectAPI from "./projectAPI";

export const getProjectsByUserId = createAsyncThunk("FETCH_PROJECTS_BY_USER_ID", async (user_id) => {
    try {
        const response = await projectAPI.fetchProjectsByUserId(user_id)
        //console.log(response)
        return response.data
    } catch (error) {
        console.log(error);
    }
})

export const getProjectMembersByProjectId = createAsyncThunk("FETCH_PROJECT_MEMBERS_BY_PRJ_ID", async (project_id) => {
    try {
        const response = await projectAPI.fetchProjectMemberByProjectId(project_id)
        //console.log(response)
        return response.data
    } catch (error) {
        console.log(error);
    }
})