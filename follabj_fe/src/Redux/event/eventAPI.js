import axios from "axios";

const projectUrl = (project_id) => "http://localhost:8080/project/"+project_id+"/events";

export const fetchEventsByProjectId = (project_id) => axios.get(projectUrl(project_id));

export const fetchEventsByUserId = (user_id) => 
    axios.get("http://localhost:8080/events?user_id="+user_id, {
        headers : {
            'Authorization' : "Bearer "+ localStorage.getItem("access_token")
        }
});

export const fetchEventById = (project_id, event_id) => axios.get(projectUrl(project_id)+"/"+event_id);

const leaderUrl = (project_id) => "http://localhost:8080/project/"+project_id+"/leader/events";

export const addEvent = (project_id, event) => axios.post(leaderUrl(project_id), event, {
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});

export const updateEvent = (project_id, event) => axios.put(projectUrl(project_id)+"/"+event.id+"/"+"update", event, {
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});