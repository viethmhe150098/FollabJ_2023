import {combineReducers, configureStore} from "@reduxjs/toolkit"
import authReducer from "./auth/authSlice";
import { taskReducer } from "./task/taskReducers";
import { eventReducer } from "./event/eventReducers";
import projectReducer from "./project/projectSlice";
import { responseMessageReducer } from "./responseMessage/messageReducers";
import { fileReducer } from "./file/fileReducers";
import { noteReducer } from "./note/noteReducers";

import {persistReducer , persistStore} from "redux-persist";
import storage from "redux-persist/lib/storage";
import thunk from "redux-thunk";
import { invitationReducer } from "./invitation/invitationReducers";
import { userReducer } from "./user/userReducers";
import { requestReducer } from "./leaderRequest/requestReducer";
import { userProfileReducer } from "./userProfile/userProfileReducers";

const rootReducer = combineReducers({
    responseMessage: responseMessageReducer,
    auth: authReducer,
    project: projectReducer,
    task : taskReducer,
    event : eventReducer,
    file : fileReducer,
    note : noteReducer,
    invitation: invitationReducer,
    user: userReducer,
    leaderRequest: requestReducer,
    userProfile: userProfileReducer
})

const persistConfig = {
    key: 'root',
    storage,
    whitelist: ['auth','project']
}

const persistedReducer = persistReducer(persistConfig, rootReducer)

export const store =  configureStore({
    reducer: persistedReducer,
    middleware: [thunk]
})

export const persistor = persistStore(store)