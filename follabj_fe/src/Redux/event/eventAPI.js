import instance from "../axiosInstance";

const eventUrl = (project_id) => "http://localhost:8080/project/"+project_id+"/events";

const leaderEventUrl = (project_id) => "http://localhost:8080/project/"+project_id+"/leader/events";


export const fetchEventsByProjectId = (project_id) => instance.get(eventUrl(project_id));

export const fetchEventsByUserId = (user_id) => 
    instance.get("http://localhost:8080/events?user_id="+user_id);

export const fetchEventById = (project_id, event_id) => instance.get(eventUrl(project_id)+"/"+event_id);


export const addEvent = (project_id, event) => instance.post(leaderEventUrl(project_id), event);

export const updateEvent = (project_id, event) => instance.put(leaderEventUrl(project_id)+"/"+event.id+"/"+"update", event);

export const deleteEvent = (project_id, event_id) => instance.delete(leaderEventUrl(project_id)+"/"+event_id+"/"+"delete");