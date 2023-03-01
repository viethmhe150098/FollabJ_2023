import axios from "axios";

const projectUrl = (project_id) => "http://localhost:8080/project/"+project_id+"/tasks";

export const fetchTasksByProjectId = (project_id) => axios.get(projectUrl(project_id),{
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});

export const fetchTaskById = (project_id, task_id) => axios.get(projectUrl(project_id)+"/"+task_id);

export const addTask = (project_id, task) => axios.post(projectUrl(project_id), task);

export const updateTask = (project_id, task) => axios.put(projectUrl(project_id)+"/"+task.id+"/update", task);

export const deleteTask = (project_id,task_id) => axios.delete(projectUrl(project_id)+"/"+task_id+"/delete")