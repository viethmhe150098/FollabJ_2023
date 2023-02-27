// import Kanban from "../../components/Sections/Kanban";
import AddTask from "../../components/Modals/AddTask";
import Sidebar from "../../components/Nav/Sidebar";
import AboutProject from "./AboutProject";
// import Note from "../components/Sections/Note";
import MeetingCreation from "../ProjectWorkspace/MeetingCreation";
import Calendar from "../ProjectWorkspace/Calendar";

import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";
import CalendarView from "../../components/Elements/CalendarView";
import Kanban from "../../components/Sections/Kanban";




export default function TaskManagement() {
  return (
    <Router>

      <Sidebar>
        <Switch>
          {/* Task */}
          <Route path="/task">
            <Kanban />
          </Route>

          {/* Notes */}
          {/* <Route path="/notes">
            <Note />
          </Route> */}
          
          {/* Notes */}
          <Route path="/events">
            <Calendar />
          </Route>

          {/* Meeting */}
          <Route path="/meetingCreate">
          <MeetingCreation />
          </Route>

          <Route path="/aboutProject">
            <AboutProject />
          </Route>
          
        </Switch>
      </Sidebar>
    </Router>
  );
}