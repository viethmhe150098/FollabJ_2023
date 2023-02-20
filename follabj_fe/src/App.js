import React, { useState } from 'react';

// Screens
import Home from "./screens/Landing/Home"
import ProjectList from "./screens/ProjectList/ProjectList"
import Login from "./screens/Authen/Login.jsx";
import SignUp from './screens/Authen/SignUp.jsx';
import AboutProject from './screens/ProjectWorkspace/AboutProject'
import ProjectManagement from "./screens/ProjectWorkspace/ProjectManagement";
// import Register from "./screens/Register.jsx";
import LoggedLanding from './screens/Landing/LoggedLanding.jsx';


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

          {/* Task */}
          <Route path="/projectManagement">
            <ProjectManagement />
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

          <Route path="/loggedLanding">
            <LoggedLanding />
          </Route>

          {/* Meeting */}
          {/* <Route path="/meeting">
            <TaskManagement />
          </Route> */}
          {/* Meeting */}
          {/* <Route path="/events">
          <TaskManagement />
          </Route> */}

          {/* <Route path="/projects">
            <ProjectList />
          </Route> */}
          <Route path="/aboutProject" >
            <AboutProject/>
          </Route>
          <Route path="/projects">
            <ProjectList/>
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

