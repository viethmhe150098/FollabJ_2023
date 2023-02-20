import React, { useState } from 'react';

// Screens
import Home from "./screens/Landing/Home"
import ProjectList from "./screens/ProjectList/ProjectList"
import Login from "./screens/Authen/Login.jsx";
import SignUp from './screens/Authen/SignUp.jsx';
// import Register from "./screens/Register.jsx";


//Library
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";
import AboutProject from './screens/ProjectWorkspace/AboutProject/AboutProject';

// import TaskManagement from "./screens/TaskManagement.jsx";

export default function App() {
 
  return (
    <>
      <Router>
        <Switch>

          {/* Task */}
          {/* <Route path="/task">
            <TaskManagement />
          </Route> */}
          {/* Task */}
          {/* <Route path="/notes">
            <TaskManagement />
          </Route>
          </Route> */}

          <Route path="/login">
            <Login />
          </Route> 

          {/* Register */}
          <Route path="/register">
            <SignUp />
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

