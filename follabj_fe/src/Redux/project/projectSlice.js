import { createSlice } from "@reduxjs/toolkit";
import { getProjectsByUserId } from "./projectActions";

const projectSlice = createSlice({
    name:"project",
    initialState:{
        projects: {
            allProjects:null,
            isFetching:false,
            error:false
        },
        msg:"",
    },
    reducers:{
        getProjectStart: (state)=>{
            state.projects.isFetching=true;
        },
        getProjectSuccess: (state,action) =>{
            state.projects.isFetching=false;
            state.projects.allProjects = action.payload;
        },
        getProjectFailed:(state)=>{
            state.projects.isFetching=false;
            state.projects.error=true;
        },

        createProjectStart: (state)=>{
            state.projects.isFetching=true;
        },
        createProjectSuccess: (state,action) =>{
            state.projects.isFetching=false;
            state.msg = action.payload;
        },
        createProjectFailed:(state,action)=>{
            state.projects.isFetching=false;
            state.projects.error=true;
            state.msg = action.payload;
        },
    }
})

export const {
    getProjectStart,
    getProjectSuccess,
    getProjectFailed,

    createProjectStart,
    createProjectSuccess,
    createProjectFailed,

} = projectSlice.actions;

export default projectSlice.reducer;