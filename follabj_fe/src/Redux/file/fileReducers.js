import { createReducer } from "@reduxjs/toolkit/dist";
import { getFiles, uploadFile } from "./fileActions";

const initialState = []


export const fileReducer = createReducer(initialState, (builder) => {
    builder
        .addCase(getFiles.fulfilled, (state, action) => {
            return action.payload.data
        })
        .addCase(uploadFile.fulfilled, (state, action) => {
            state.push(action.payload)
            return state
        })
})