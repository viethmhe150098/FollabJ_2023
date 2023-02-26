import { createAsyncThunk } from "@reduxjs/toolkit/dist"
import * as eventAPI from "./eventAPI";

export const getEventsByProjectId = createAsyncThunk("FETCH_EVENTS_BY_PRJ_ID", async (project_id) => {
    try {
        const response = await eventAPI.fetchEventsByProjectId(project_id)
        //console.log(response)
        return response.data
    } catch (error) {
        console.log(error);
    }
})

export const getEventById = createAsyncThunk("FETCH_EVENT_BY_EVENT_ID", async (event_id) => {
    try {
        const response = await eventAPI.fetchEventsByProjectId(event_id)
        return response.data
    } catch (error) {
        console.log(error);
    }
})

export const addEvent = createAsyncThunk("ADD_EVENT", async (event) => {
    try {
        const response = await eventAPI.addEvent(event.project_id, event)
        return response.data
    } catch (error) {
        console.log(error);
    }
})