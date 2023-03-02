import React, { useState } from 'react';

// Screens
import Home from "./screens/Landing/Home"
import ProjectList from "./screens/ProjectList/ProjectList"
import Login from "./screens/Authen/Login.jsx";
import SignUp from './screens/Authen/SignUp.jsx';
import AboutProject from './screens/ProjectWorkspace/AboutProject'
import ProjectManagement from "./screens/ProjectWorkspace/ProjectManagement";
import UserProfile from './screens/UserProfile';
import Meeting from './screens/ProjectWorkspace/Meeting';
// import Register from "./screens/Register.jsx";


//Library
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";





export default function App() {

  return (
    <>
      <Router>
        <Switch>

          <Route path="/projectManagement">
            <ProjectManagement />
          </Route>

          <Route path="/tasks">
            <ProjectManagement />
          </Route>

          {/* Meeting */}
          <Route path="/meetingCreate">
            <ProjectManagement />
          </Route>

          <Route path="/meeting">
            <Meeting />
          </Route>

          {/* Task */}
          {/* <Route path="/notes">
            <TaskManagement />
          </Route>
          </Route> */}

          <Route path="/login">
            <Login />
          </Route>

          {/* Register */}
          <Route path="/signup">
            <SignUp />
          </Route>
          <Route path="/profile">
            <UserProfile />
          </Route>

          {/* Meeting */}
          <Route path="/events">
          <ProjectManagement />
          </Route>

          {/* <Route path="/projects">
            <ProjectList />
          </Route> */}
          <Route path="/aboutProject" >
            <AboutProject />
          </Route>
          <Route exact path="/projects">
            <ProjectList />
          </Route>
          {/* Landing */}
          <Route exact path="/">
            <Home />
          </Route>

        </Switch>
      </Router>
    </>

  );
}

