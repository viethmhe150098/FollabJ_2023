import { createAsyncThunk } from "@reduxjs/toolkit";
import * as noteApi from "./noteApi";

export const getNotesByUserId = createAsyncThunk("FETCH_NOTES_BY_USER_ID", async (user_id)=>{
    try {
        const response = await noteApi.fetchNotesByUserId(user_id)
        return response.data
    } catch (error) {
        throw error
    }
})

export const addNote = createAsyncThunk("ADD_NOTE", async ({user_id, note}) => {
    try {
        const response = await noteApi.addNote(user_id, note)
        return response.data
    } catch (error) {
        throw error
    }
})

export const updateNote = createAsyncThunk("UPDATE_NOTE", async ({user_id, note}) => {
    try {
        const response = await noteApi.updateNote(user_id, note)
        return response.data
    } catch (error) {
        throw error
    }
})