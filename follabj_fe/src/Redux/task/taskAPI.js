import instance from "../axiosInstance";

const taskUrl = (project_id) => "/project/"+project_id+"/tasks";
const leaderTaskUrl = (project_id) => "/project/"+project_id+"/leader/tasks";

export const fetchTasksByProjectId = (project_id) => instance.get(taskUrl(project_id));

export const fetchTaskById = (project_id, task_id) => instance.get(taskUrl(project_id)+"/"+task_id);

export const addTask = (project_id, task) => instance.post(leaderTaskUrl(project_id), task);

export const updateTask = (project_id, task) => instance.put(leaderTaskUrl(project_id)+"/"+task.id+"/update", task);

export const changeColumn = (project_id, task_id, status, columnPosition) => instance.put(leaderTaskUrl(project_id)+"/"+task_id+"/changeColumn", null, {
    params : {
        status,
        columnPosition
    }
});

export const changePosition = (project_id, task_id, columnPosition) => instance.put(leaderTaskUrl(project_id)+"/"+task_id+"/changePosition", null, {
    params : {
        columnPosition
    }
});

export const deleteTask = (project_id,task_id) => instance.delete(leaderTaskUrl(project_id)+"/"+task_id+"/delete")