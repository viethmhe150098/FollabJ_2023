import {configureStore} from "@reduxjs/toolkit"
import authReducer from "./auth/authSlice";
import { taskReducer } from "./task/taskReducers";
import { eventReducer } from "./event/eventReducers";
import projectReducer from "./project/projectSlice";

export default configureStore({
    reducer:{
        auth: authReducer,
        project: projectReducer,
        task : taskReducer,
        event : eventReducer
    },
})