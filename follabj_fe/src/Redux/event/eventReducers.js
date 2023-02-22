import { createReducer } from "@reduxjs/toolkit/dist";
import moment from "moment/moment";
import { getEventsByProjectId, getEventById } from "./eventActions";

const initialState = []

export const eventReducer = createReducer(initialState, (builder) => {
    builder
        .addCase(getEventsByProjectId.fulfilled, (state, action) => {
            action.payload.map((event) =>{
                const converted = {
                    title : event.title,
                    describe : event.description,
                    id: event.id,
                    start: moment(event.startDate).format("ddd DD MMM YY LT"),
                    end: moment(event.endDate).format("ddd DD MMM YY LT")
                }
                state.push(converted)
            })
            return state
        })
        .addCase(getEventById.fulfilled, (state, action) => {
            console.log(action.payload)
        })
})