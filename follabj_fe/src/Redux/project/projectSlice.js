import { createSlice } from "@reduxjs/toolkit";
import { acceptInvitation } from "../invitation/invitationActions";
import { assignLeader, deleteMember, deleteProject, getProjectMembersByProjectId, getProjectsByUserId, inviteMember, leaveProject, updateProject, } from "./projectActions";

const projectSlice = createSlice({
    name:"project",
    initialState:{
        currentProject: {
            id : null,
            userRole: null,
            members: []
        },
        projects: {
            allProjects:[],
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

        setCurrentProjectId: (state, action) => {
            state.currentProject.id = action.payload;
        },
        setCurrentProjectUserRole : (state, action) => {
            state.currentProject.userRole = action.payload;
        },
    },
    extraReducers: (builder) => {

        builder
            .addCase(getProjectsByUserId.fulfilled, (state, action) => {
                state.projects.allProjects = action.payload.projects
            })
            .addCase(getProjectMembersByProjectId.fulfilled, (state, action) => {
                state.currentProject.members = action.payload
            })
            .addCase(updateProject.fulfilled, (state, action) => {
                state.currentProject.allProjects = state.currentProject.allProjects.map((project) => project.id == action.payload.id ? action.payload : project)
            })
            .addCase(deleteProject.fulfilled, (state, action) => {
                state.currentProject.allProjects = state.currentProject.allProjects.filter((project) => project.id != action.payload)
            })
            .addCase(acceptInvitation.fulfilled, (state, action) => {
                state.projects.allProjects.push(action.payload.joinedProject)
            })
            .addCase(leaveProject.fulfilled, (state, action) => {
                state.projects.allProjects = state.projects.allProjects.filter((project) => project.id != action.payload)
            })
            .addCase(assignLeader.fulfilled, (state, action) => {
                
            })
            .addCase(deleteMember.fulfilled, (state, action) => {
                state.currentProject.members = state.currentProject.members.filter((member)=> member.id != action.payload)
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

    setCurrentProjectId,
    setCurrentProjectUserRole

} = projectSlice.actions;

export default projectSlice.reducer;