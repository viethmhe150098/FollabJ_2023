import axios from "axios";

const eventUrl = (project_id) => "http://localhost:8080/project/"+project_id+"/events";

const leaderEventUrl = (project_id) => "http://localhost:8080/project/"+project_id+"/leader/events";


export const fetchEventsByProjectId = (project_id) => axios.get(eventUrl(project_id));

export const fetchEventsByUserId = (user_id) => 
    axios.get("http://localhost:8080/events?user_id="+user_id, {
        headers : {
            'Authorization' : "Bearer "+ localStorage.getItem("access_token")
        }
});

export const fetchEventById = (project_id, event_id) => axios.get(eventUrl(project_id)+"/"+event_id);


export const addEvent = (project_id, event) => axios.post(leaderEventUrl(project_id), event, {
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});

export const updateEvent = (project_id, event) => axios.put(leaderEventUrl(project_id)+"/"+event.id+"/"+"update", event, {
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});

export const deleteEvent = (project_id, event_id) => axios.delete(leaderEventUrl(project_id)+"/"+event_id+"/"+"delete", {
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});