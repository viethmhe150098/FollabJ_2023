import React, { useState } from 'react';

// Screens
import Home from "./screens/Landing/Home"
import ProjectList from "./screens/ProjectList/ProjectList"
import Login from "./screens/Authen/Login.jsx";
import SignUp from './screens/Authen/SignUp.jsx';
import LoggedLanding from './screens/Landing/LoggedLanding.jsx';


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

