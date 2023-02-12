const TasksReducer = (state=[], action)=>{

    switch (action.type) {
        case "SHOW_TASKS":
            return action.payload
        case "ADD_TASK":
            // console.log(state)
            // console.log(action.payload)
            
            const column = state.filter((column) => column.id == action.payload.statusId)[0]
            
            //console.log(column)
            
            column.tasks.push(action.payload.taskData)
            const updatedColumn = {...column, tasks: column.tasks}

            //console.log(updatedColumn)

            const updatedState = state.map((column) => column.id != action.payload.statusId ? column : updatedColumn)
            
            //console.log(updatedState)
            
            return updatedState
        default:
            return state
}
}

export default TasksReducer