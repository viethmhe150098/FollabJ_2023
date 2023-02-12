
import { task } from "../Axios/task"
import { addError, removeError } from "./errorsAction"

export const showTask = (task)=>{
    //console.log("task to be shown on the modal: ", task)
    return{
        type: "SHOW_TASK",
        payload: task
    }
}

export const showTasks = (tasks)=>{
    
    return{
        type: "SHOW_TASKS",
        payload: tasks
    }
}

export const ShowTaskApi = id => async dispatch => {
     
    //i won't get the task from redux store as it is safer to
    //keep updated with db.
    const result = await task.get(`/${id}/show`);

    try{
        const {_id, assignee, category, project_id, title, task_status_id} = await result.data;
        const convertedTask= {
            id: _id,
            assignee,
            category,
            title,
            project_id,
            task_status_id
        }
        await dispatch(showTask(convertedTask))
    }catch(err){
         const error =await err.data.message;
         return error
    }
}

export const ShowTasksApi = () => async dispatch => {
     console.log("started fetching the showtasks api")
    //i won't get the task from redux store as it is safer to
    //keep updated with db.
    const result = await task.get("/");
    console.log(result)
    try{
        const tasksList = result.data.list
        dispatch(showTasks(tasksList))
    }catch(err){
         console.log(err)
    }
}

const addTask = (taskData, statusId)=>{
    return{
      type: "ADD_TASK",
      payload: {taskData, statusId}
    }
}


export const addTaskApi = ({taskData, statusId}) => async dispatch =>{
    const result = await task.post("/", {
         assignee : taskData.assignee,
         task_title: taskData.title,
       })
       .then(res=>{
        
        if(res && res.data){
            console.log("task from the api going to the reducer: ", res.data)
            dispatch(addTask(taskData = res.data, statusId))
            //dispatch(removeError())
            }
       })
       .catch(res=>{
        console.log("catch response, ", res)
        if(res.data){

            console.log(res.data)
            dispatch(addError(res.data));
        }
    })
       
}



 
