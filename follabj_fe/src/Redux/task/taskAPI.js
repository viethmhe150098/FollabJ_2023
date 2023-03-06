import axios from "axios";

const taskUrl = (project_id) => "http://localhost:8080/project/"+project_id+"/tasks";
const leaderTaskUrl = (project_id) => "http://localhost:8080/project/"+project_id+"/leader/tasks";

export const fetchTasksByProjectId = (project_id) => axios.get(taskUrl(project_id),{
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});

export const fetchTaskById = (project_id, task_id) => axios.get(taskUrl(project_id)+"/"+task_id);

export const addTask = (project_id, task) => axios.post(leaderTaskUrl(project_id), task, {
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});

export const updateTask = (project_id, task) => axios.put(leaderTaskUrl(project_id)+"/"+task.id+"/update", task , {
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});

export const deleteTask = (project_id,task_id) => axios.delete(leaderTaskUrl(project_id)+"/"+task_id+"/delete", {
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
})