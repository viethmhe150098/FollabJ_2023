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