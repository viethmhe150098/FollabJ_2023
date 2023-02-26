import axios from "axios";

const projectUrl = (project_id) => "http://localhost:8080/project/"+project_id+"/events";

export const fetchEventsByProjectId = (project_id) => axios.get(projectUrl(project_id));

export const fetchEventById = (project_id, event_id) => axios.get(projectUrl(project_id)+"/"+event_id);

export const addEvent = (project_id, event) => axios.post(projectUrl(project_id), event);

export const updateEvent = (project_id, event) => axios.put(projectUrl(project_id)+"/"+event.id+"/"+"update", event);