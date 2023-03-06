import { createReducer } from "@reduxjs/toolkit/dist";
import { getTasksByProjectId, getTaskById, addTask, updateTask, deleteTask } from "./taskActions";

const initialState = []

export const taskReducer = createReducer(initialState, (builder) => {
    builder
        .addCase(getTasksByProjectId.fulfilled, (state, action) => {
            return action.payload
        })
        .addCase(getTaskById.fulfilled, (state, action) => {
            console.log(action.payload)
        })
        .addCase(addTask.fulfilled, (state, action) => {
            // return [...state, action.payload]
            state.push(action.payload)
        })
        .addCase(updateTask.fulfilled, (state, action) => {
            return state.map((task) => task.id == action.payload.id ? action.payload : task)
        })
        .addCase(deleteTask.fulfilled, (state, action) => {
            return state.filter((task) => task.id != action.payload)
        })
})