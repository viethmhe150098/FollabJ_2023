import { createReducer } from "@reduxjs/toolkit/dist";
import { getEventsByUserId } from "../event/eventActions";
import { getTasksByProjectId } from "../task/taskActions";
import { clearMessages } from "./messageActions";

const initialState = {
    success: "",
    error: ""
}

export const responseMessageReducer = createReducer(initialState, (builder) => {
    builder
        .addCase(clearMessages, (state, action) => {
            return {
                success: "",
                error: ""
            }
        })
        .addCase(getTasksByProjectId.fulfilled, (state, action) => {
            //console.log(action)
            state.success = "Get tasks successful"
        })
        .addCase(getTasksByProjectId.rejected, (state, action) => {
            //console.log(action)
            state.error = action.error.code+" : "+action.error.message
        })
        .addCase(getEventsByUserId.fulfilled, (state, action) => {
            //console.log(action)
            state.success = "Get events successful"
        })
        .addCase(getEventsByUserId.rejected, (state, action) => {
            //console.log(action)
            state.error = action.error.code+" : "+action.error.message
        })
})
