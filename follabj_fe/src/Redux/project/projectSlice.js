import { createSlice } from "@reduxjs/toolkit";
import { getProjectMembersByProjectId, getProjectsByUserId } from "./projectActions";

const projectSlice = createSlice({
    name:"project",
    initialState:{
        currentProject: {
            id : null,
            user_role: null,
            members: []
        },
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
    },
    extraReducers: (builder) => {

        builder
            .addCase(getProjectsByUserId.fulfilled, (state, action) => {
                state.projects.allProjects = action.payload
            })
            .addCase(getProjectMembersByProjectId.fulfilled, (state, action) => {
                state.currentProject.members.push(action.payload)
            })
    },
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