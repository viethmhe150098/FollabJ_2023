import { createAsyncThunk } from "@reduxjs/toolkit";
import { toast } from "react-toastify";
import * as projectAPI from "./projectAPI";

export const fetchAllProjects = createAsyncThunk("FETCH_ALL_PROJECTS", async (page_number) => {
    try {
        const response = await projectAPI.fetchAllProjects(page_number)
        //console.log(response)
        return response.data
    } catch (error) {
        console.log(error);
    }
})

export const getProjectStatistics = createAsyncThunk("GET_PROJECT_STATISTICS", async () => {
    try {
        const response = await projectAPI.getProjectStatistics()
        //console.log(response)
        return response.data
    } catch (error) {
        console.log(error);
    }
})

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

export const inviteMember = createAsyncThunk("INVITE_MEMBER", async ({project_id, userdto}) => {
    try {
        const response = await projectAPI.inviteMember(project_id, userdto)
        //console.log(response)
        return response.data
    } catch (error) {
        toast.error('This email address is not registered!')
        console.log(error);
    }
})

export const updateProject = createAsyncThunk("UPDATE_PROJECT", async({project_id, project}) => {
    try {
        const response = await projectAPI.updateProject(project_id, project)

        //console.log(response)
        return response
    } catch (error) {
        console.log(error);
    }
})

export const deleteProject = createAsyncThunk("DELETE_PROJECT", async(project_id) => {
    try {
        await projectAPI.deleteProject(project_id)

        //console.log(response)
        return project_id
    } catch (error) {
        console.log(error);
    }
})



export const assignLeader = createAsyncThunk("ASSIGN_LEADER", async({project_id, user_id, new_leader_id}) => {
    try {
        await projectAPI.assignLeader(project_id, new_leader_id)

        //console.log(response)
        return project_id
    } catch (error) {
        console.log(error);
    }
})

export const leaveProject = createAsyncThunk("LEAVE_PROJECT", async({project_id, user_id}) => {
    try {
        await projectAPI.leaveProject(project_id, user_id)

        //console.log(response)
        return project_id
    } catch (error) {
        console.log(error);
    }
})

export const deleteMember = createAsyncThunk("DELETE_MEMBER", async({project_id, member_id}) => {
    try {
        const response = await projectAPI.deleteMember(project_id, member_id)
        //console.log(response)
        return member_id
    } catch (error) {
        console.log(error);
    }
})

export const deactiveProject = createAsyncThunk("DEACTIVE_PROJECT", async(project_id) => {
    try {
        const response = await projectAPI.deactiveProject(project_id)
        //console.log(response)
        return response.data
    } catch (error) {
        console.log(error);
    }
})