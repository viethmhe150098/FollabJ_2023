import { createAsyncThunk } from "@reduxjs/toolkit/dist"
import * as taskAPI from "./taskAPI";

export const getTasksByProjectId = createAsyncThunk("FETCH_TASKS_BY_PRJ_ID", async (project_id) => {
    try {
        const response = await taskAPI.fetchTasksByProjectId(project_id)
        //console.log(response)
        return response.data
    } catch (error) {
        console.log(error);
        // throw error
    }
})

export const getTaskById = createAsyncThunk("FETCH_TASK_BY_TASK_ID", async ({project_id, task_id}) => {
    try {
        const response = await taskAPI.fetchTaskById(project_id, task_id)
        return response.data
    } catch (error) {
        console.log(error);
    }
})

export const addTask = createAsyncThunk("ADD_TASK", async ({project_id, task}) => {
    try {
        const response = await taskAPI.addTask(project_id, task)
        return response.data
    } catch (error) {
        console.log(error);
    }
})

export const updateTask = createAsyncThunk("UPDATE_TASK", async ({project_id, task}) => {
    try {
        const response = await taskAPI.updateTask(project_id, task)
        return response.data
    } catch (error) {
        console.log(error);
    }
})

export const deleteTask = createAsyncThunk("DELETE_TASK", async ({project_id, task_id}) => {
    try {
        const response = await taskAPI.deleteTask(project_id,task_id)
        return task_id
    } catch (error) {
        console.log(error);
    }
})
