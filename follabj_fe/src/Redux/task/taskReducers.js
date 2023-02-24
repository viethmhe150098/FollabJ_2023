import { createReducer } from "@reduxjs/toolkit/dist";
import { getTasksByProjectId, getTaskById, addTask, updateTask, deleteTask } from "./taskActions";

const initialState = [
    {
        id : 1,
        tasks : []
    },
    {
        id : 2,
        tasks : []
    },
    {
        id : 3,
        tasks : []
    }
]

export const taskReducer = createReducer(initialState, (builder) => {
    builder
        .addCase(getTasksByProjectId.fulfilled, (state, action) => {
            // const newState = state;

            // action.payload.map((task) => {
                
            //     newState.map((column)=> column.id == task.statusId ? () => {console.log(column.id);column.tasks.push(task)} : () => {console.log(column.id); return column })
            //     //newState.map((column) => console.log(column))
            // })
            //console.log(action.payload)
            //console.log(newState)
            action.payload.map((task) => state[0].tasks.push(task))
        })
        .addCase(getTaskById.fulfilled, (state, action) => {
            console.log(action.payload)
        })
        .addCase(addTask.fulfilled, (state, action) => {
            // return [...state, action.payload]
            state[0].tasks.push(action.payload)
        }).addCase(updateTask.fulfilled, (state, action) => {
            return state.map((task) => task.id == action.payload.id ? action.payload : task)
        })
})