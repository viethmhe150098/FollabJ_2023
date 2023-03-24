import { createAsyncThunk } from "@reduxjs/toolkit";
import * as fileAPI from "./fileAPI";
import { toast } from 'react-toastify';
export const getFiles = createAsyncThunk("GET_FILES", async ({project_id, page_number}) => {
    try {
        const response = await fileAPI.fetchFiles(project_id, page_number)
        //console.log(response)
        return response.data
    } catch (error) {
        console.log(error);
    }
})

export const uploadFile = createAsyncThunk("UPLOAD_FILE", async ({project_id, data}) => {
    try {
        const response = await fileAPI.uploadFile(project_id, data)
        toast.success("Upload file successfully!"); // display the toast notification
        //console.log(response)
        return response.data
    } catch (error) {
        console.log(error);
    }
})

