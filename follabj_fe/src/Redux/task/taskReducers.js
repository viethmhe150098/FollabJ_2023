import { createReducer } from "@reduxjs/toolkit/dist";
import { getTasksByProjectId, getTaskById, addTask, updateTask, deleteTask } from "./taskActions";

const initialState = []

export const taskReducer = createReducer(initialState, (builder) => {
    builder
        .addCase(getTasksByProjectId.fulfilled, (state, action) => {
            // // const newState = state;

            // action.payload.map((task) => {
                
            //     state.map((column)=> column.id == task.statusId ? () => {console.log(column.id);column.tasks.push(task)} : () => {console.log(column.id); return column })
            //     //newState.map((column) => console.log(column))
            // })
            // //action.payload.map((task) => state[0].tasks.push(task))
            return action.payload
        })
        .addCase(getTaskById.fulfilled, (state, action) => {
            console.log(action.payload)
        })
        .addCase(addTask.fulfilled, (state, action) => {
            // return [...state, action.payload]
            state.push(action.payload)
        }).addCase(updateTask.fulfilled, (state, action) => {
            return state.map((task) => task.id == action.payload.id ? action.payload : task)
        })
})