import React, { useState } from 'react';

// Screens
import ProjectList from "./screens/ProjectList/ProjectList"
import Login from "./screens/Authen/Login.jsx";
import SignUp from './screens/Authen/SignUp.jsx';
import AboutProject from './screens/ProjectWorkspace/About/AboutProject'
import ProjectManagement from "./screens/ProjectWorkspace/ProjectManagement";
import UserProfile from './screens/UserProfile';
import Meeting from './screens/ProjectWorkspace/Meeting/Meeting';
import ForgotPassword from "./screens/Authen/ForgotPassword";
// import Register from "./screens/Register.jsx";
import { isLoggedIn } from '../src/Redux/auth/auth';
import ErrorPage from '../src/screens/ErrorPage'
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
import { ToastContainer} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import ConfirmToken from './screens/ConfirmToken/ConfirmToken';




export default function App() {

  return (
    <>
      <ToastContainer />
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
            {isLoggedIn() ? <Landing /> : <Login />}
          </Route>
          {/* Register */}
          <Route path="/signup">
            {isLoggedIn() ? <Landing /> : <SignUp />}
          </Route>
          <Route path="/forgot">
            {isLoggedIn() ? <Landing /> : <ForgotPassword />}
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
          <Route path="/error">
            <ErrorPage />
          </Route>
          <Route exact path="/">
            <Landing />
          </Route>
          <Route path="/confirm/:token">
            <ConfirmToken />
          </Route>

        </Switch>
      </Router>
    </>

  );
}

