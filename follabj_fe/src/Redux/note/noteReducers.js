import { createReducer } from "@reduxjs/toolkit";
import moment from "moment";
import { addNote, getNotesByUserId, updateNote } from "./noteActions";


const initialState = []

export const noteReducer = createReducer(initialState,(builder)=>{
    builder
    .addCase(getNotesByUserId.fulfilled,(state,action)=>{
        const newState = []
        action.payload.map((note)=>{
            const converted = {
                id : note.id,
                title: note.title,
                content : note.content,
                createdDate : moment(note.createdDate).format("yyyy-MM-DD hh:mm:ss"),
                updatedDate : moment(note.updatedDate).format("yyyy-MM-DD hh:mm:ss")
            }
            newState.push(converted)
        })
        return newState
    })
    .addCase(addNote.fulfilled, (state, action) => {
        const converted = {
            id : action.payload.id,
            title: action.payload.title,
            content : action.payload.content,
            createdDate : moment(action.payload.createdDate).format("yyyy-MM-DD hh:mm:ss"),
            updatedDate : moment(action.payload.updatedDate).format("yyyy-MM-DD hh:mm:ss")
        }
        state.push(converted)
        return state
    })
    .addCase(updateNote.fulfilled, (state, action) => {
        state.map((note) => note.id == action.payload.id ? action.payload : note)
        return state
    })
})