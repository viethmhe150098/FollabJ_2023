import React, { useState } from 'react';

// Screens
import Home from "./screens/Landing/Home"
import ProjectList from "./screens/ProjectList/ProjectList"
// import Login from "./screens/Login.jsx";
// import Register from "./screens/Register.jsx";


//Library
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";

// import TaskManagement from "./screens/TaskManagement.jsx";

export default function App() {
  const [isOnProjectsPage, setIsOnProjectsPage] = useState(false);
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

          <Route path="/login">
            <Login />
          </Route> */}

          {/* Register */}
          {/* <Route path="/register">
            <Register />
          </Route> */}

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
          <Route path="/projects" render={() => <ProjectList setIsOnProjectsPage={setIsOnProjectsPage} />} />
          {/* Landing */}
          <Route exact path="/">
            <Home />
          </Route>


        </Switch>
      </Router>
    </>

  );
}

