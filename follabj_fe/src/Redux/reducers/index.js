
import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import EventReducer from "./eventReducer";
import EventsReducer from "./eventsReducer";
import modalReducer from "./modelReducer"
import TasksReducer from "./tasksReducer";

const rootReducer = combineReducers({
    tasks : TasksReducer ,
    event: EventReducer ,
    events: EventsReducer,
    modalStatus: modalReducer,
    error: errorReducer
}) 

export default rootReducer;

