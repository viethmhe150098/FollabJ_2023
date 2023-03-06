import { createReducer } from "@reduxjs/toolkit";
import moment from "moment";
import { getNotesByUserId } from "./noteActions";


const initialState = []

export const noteReducer = createReducer(initialState,(builder)=>{
    builder
    .addCase(getNotesByUserId.fulfilled,(state,action)=>{
        action.payload.map((note)=>{
            const converted = {
                id : note.id,
                content : note.content,
                createdDate : moment(note.createdDate).format("yyyy-MM-DD hh:mm:ss"),
                updatedDate : moment(note.updatedDate).format("yyyy-MM-DD hh:mm:ss")
            }
            state.push(converted)
        })
        return state
    })
})