import { createReducer } from "@reduxjs/toolkit/dist";
import moment from "moment/moment";
import { getEventsByProjectId, getEventById, addEvent, getEventsByUserId } from "./eventActions";

const initialState = []

export const eventReducer = createReducer(initialState, (builder) => {
    builder
        .addCase(getEventsByProjectId.fulfilled, (state, action) => {
            action.payload.map((event) =>{
                const converted = {
                    title : event.title,
                    describe : event.description,
                    id: event.id,
                    start: moment(event.startDate).format("yyyy-MM-DD hh:mm:ss"),
                    end: moment(event.endDate).format("yyyy-MM-DD hh:mm:ss")
                }
                state.push(converted)
            })
            return state
        })
        .addCase(getEventsByUserId.fulfilled, (state, action) => {
            action.payload.map((event) =>{
                const converted = {
                    title : event.title,
                    describe : event.description,
                    id: event.id,
                    start: moment(event.startDate).format("yyyy-MM-DD hh:mm:ss a"),
                    end: moment(event.endDate).format("yyyy-MM-DD hh:mm:ss a"),
                    participantList: event.participantList
                }
                state.push(converted)
            })
            return state
        })
        .addCase(getEventById.fulfilled, (state, action) => {
            console.log(action.payload)
        }).addCase(addEvent.fulfilled, (state, action) => {
            const converted = {
                title : action.payload.title,
                describe : action.payload.description,
                id: action.payload.id,
                start: moment(action.payload.startDate).format("yyyy-MM-DD hh:mm:ss a"),
                end: moment(action.payload.endDate).format("yyyy-MM-DD hh:mm:ss a"),
                participantList: action.payload.participantList
            }
            state.push(converted)
            return state
        })
})