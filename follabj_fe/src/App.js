import React, { useState } from 'react';

// Screens
import ProjectList from "./screens/ProjectList/ProjectList"
import Login from "./screens/Authen/Login.jsx";
import SignUp from './screens/Authen/SignUp.jsx';
import AboutProject from './screens/ProjectWorkspace/About/AboutProject'
import ProjectManagement from "./screens/ProjectWorkspace/ProjectManagement";
import UserProfile from './screens/UserProfile';
import Meeting from './screens/ProjectWorkspace/Meeting/Meeting';
// import Register from "./screens/Register.jsx";


//Library
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";
import Landing from './screens/Landing/Landing';
import AdminDashboard from './screens/Admin/AdminDashboard';
import AdminRoute from './components/Routes/AdminRoute';
import AuthenciatedRoute from './components/Routes/AuthenciatedRoute';





export default function App() {

  return (
    <>
      <Router>
        <Switch>

          <AuthenciatedRoute path="/projectManagement">
            <ProjectManagement />
          </AuthenciatedRoute>

          <AuthenciatedRoute path="/tasks">
            <ProjectManagement />
          </AuthenciatedRoute>

          {/* Meeting */}
          <AuthenciatedRoute path="/meetingCreate">
            <ProjectManagement />
          </AuthenciatedRoute>

          <AuthenciatedRoute path="/meeting">
            <Meeting />
          </AuthenciatedRoute>

          <AuthenciatedRoute path="/notes">
            <ProjectManagement />
          </AuthenciatedRoute>

          <AuthenciatedRoute exact path="/noteEdit">
            <ProjectManagement />
          </AuthenciatedRoute>

          <AuthenciatedRoute path="/files">
            <ProjectManagement />
          </AuthenciatedRoute>

          <AuthenciatedRoute path="/chat">
            <ProjectManagement />
          </AuthenciatedRoute>

          <Route path="/login">
            <Login />
          </Route>

          {/* Register */}
          <Route path="/signup">
            <SignUp />
          </Route>
          <AuthenciatedRoute path="/profile">
            <UserProfile />
          </AuthenciatedRoute>

          <AuthenciatedRoute path="/events">
            <ProjectManagement />
          </AuthenciatedRoute>

          <AuthenciatedRoute path="/aboutProject" >
            <ProjectManagement />
          </AuthenciatedRoute>

          <AuthenciatedRoute exact path="/projects">
            <ProjectList />
          </AuthenciatedRoute>

          <AdminRoute path="/admin" >
            <AdminDashboard />
          </AdminRoute>
          
          <Route exact path="/">
            <Landing />
          </Route>

        </Switch>
      </Router>
    </>

  );
}

