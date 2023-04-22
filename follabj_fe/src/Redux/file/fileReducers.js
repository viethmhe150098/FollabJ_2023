import { createReducer } from "@reduxjs/toolkit/dist";
import { getFiles, uploadFile } from "./fileActions";

// const initialState = [
//     {
//         id: 1,
//         fileName: "fileNamedsadsadasdsa.docx"
//     },
//     {
//         id: 2,
//         fileName: "fileNamedsadsadasdsa.docx"
//     },
//     {
//         id: 3,
//         fileName: "fileNamedsadsadasdsa.docx"
//     },
//     {
//         id: 4,
//         fileName: "fileNamedsadsadasdsa.docx"
//     },
//     {
//         id: 5,
//         fileName: "fileNamedsadsadasdsa.docx"
//     },
// ]

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