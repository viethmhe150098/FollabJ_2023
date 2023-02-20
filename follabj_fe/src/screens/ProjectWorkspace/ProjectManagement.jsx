// import Kanban from "../components/Sections/Kanban";
import Sidebar from "../../components/Nav/Sidebar";
import AboutProject from "./AboutProject";
// import Note from "../components/Sections/Note";
// import Meeting from "../screens/Meeting";
// import Calendar from "../screens/Calendar";

import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";




export default function TaskManagement() {
  return (
    <Router>

      <Sidebar>
        <Switch>
          {/* Task */}
          {/* <Route path="/task">
            <Kanban />
          </Route> */}

          {/* Notes */}
          {/* <Route path="/notes">
            <Note />
          </Route> */}
          
          {/* Notes */}
          {/* <Route path="/events">
            <Calendar />
          </Route> */}

          {/* Meeting */}
          {/* <Route path="/meeting">
            <Meeting />
          </Route> */}
          <Route path="/aboutProject">
            <AboutProject />
          </Route>
          
        </Switch>
      </Sidebar>
    </Router>
  );
}