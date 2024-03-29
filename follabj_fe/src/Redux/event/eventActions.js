import { createAsyncThunk } from "@reduxjs/toolkit/dist"
import * as eventAPI from "./eventAPI";
import { toast } from 'react-toastify';


export const getEventsByProjectId = createAsyncThunk("FETCH_EVENTS_BY_PRJ_ID", async (project_id) => {
    try {
        const response = await eventAPI.fetchEventsByProjectId(project_id)
        //console.log(response)

        return response.data

    } catch (error) {
        console.log(error);
    }
})

export const getEventsByUserId = createAsyncThunk("FETCH_EVENTS_BY_USER_ID", async (user_id) => {
    try {
        const response = await eventAPI.fetchEventsByUserId(user_id)
        return response.data

    } catch (error) {
        //console.log(error)
        throw error
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
        toast.success('Create event successfully!')
        return response.data
    } catch (error) {
        console.log(error);
    }
})

export const updateEvent = createAsyncThunk("UPDATE_EVENT", async (event) => {
    try {
        const response = await eventAPI.updateEvent(event.project_id, event)
        toast.success(`Update event ${event.title} successfully!`)
        return response.data
    } catch (error) {
        console.log(error);
    }
})

export const deleteEvent = createAsyncThunk("DELETE_EVENT", async ({ project_id, event_id, title }) => {
    try {
        const response = await eventAPI.deleteEvent(project_id, event_id)
        toast.success(`Delete event ${title} successfully!`)
        return event_id
    } catch (error) {
        console.log(error);
    }
})