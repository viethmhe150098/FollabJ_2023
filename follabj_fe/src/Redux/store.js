import {configureStore} from "@reduxjs/toolkit"
import authReducer from "./auth/authSlice";
import { taskReducer } from "./task/taskReducers";
import { eventReducer } from "./event/eventReducers";
import projectReducer from "./project/projectSlice";
import { responseMessageReducer } from "./responseMessage/messageReducers";
import { fileReducer } from "./file/fileReducers";

export default configureStore({
    reducer:{
        responseMessage: responseMessageReducer,
        auth: authReducer,
        project: projectReducer,
        task : taskReducer,
        event : eventReducer,
        file : fileReducer
    },
})