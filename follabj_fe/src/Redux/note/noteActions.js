import { createAsyncThunk } from "@reduxjs/toolkit";
import * as noteApi from "./noteApi";
import { toast } from 'react-toastify';
export const getNotesByUserId = createAsyncThunk("FETCH_NOTES_BY_USER_ID", async (user_id)=>{
    try {
        const response = await noteApi.fetchNotesByUserId()
        return response.data
    } catch (error) {
        throw error
    }
})

export const addNote = createAsyncThunk("ADD_NOTE", async ({user_id, note}) => {
    try {
        const response = await noteApi.addNote(note)
        toast.success(`Create note ${note.title} successfully!`); // display the toast notification
        return response.data
    } catch (error) {
        throw error
    }
})

export const updateNote = createAsyncThunk("UPDATE_NOTE", async ({user_id, note}) => {
    try {
        const response = await noteApi.updateNote(note)
        toast.success(`Update note ${note.title} successfully!`); // display the toast notification
        return response.data
    } catch (error) {
        throw error
    }
})

export const deleteNote = createAsyncThunk("DELETE_NOTE", async ({user_id, note}) =>{
    try {
        const response = await noteApi.deleteNote(note.id)
        toast.success(`Delete note ${note.title} successfully!`); // display the toast notification
        return note.id
    } catch (error) {
        throw error
    }
})
