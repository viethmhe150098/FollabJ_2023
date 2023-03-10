// import Kanban from "../../components/Sections/Kanban";
import AddTask from "../../components/Modals/AddTask";
import Sidebar from "../../components/Nav/Sidebar";
import AboutProject from "./AboutProject";
// import Note from "../components/Sections/Note";
import MeetingCreation from "../ProjectWorkspace/MeetingCreation";
import Calendar from "../ProjectWorkspace/Calendar";
import Note from "./NoteList";

import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";
import Kanban from "./Kanban";
import FileWorkspace from "./File";




export default function TaskManagement() {
  return (
    <Router>

      <Sidebar>
        <Switch>
          {/* Task */}
          <Route path="/tasks">
            <Kanban />
          </Route>

          {/* Notes */}
          <Route path="/notes">
            <Note />
          </Route>
          
          {/* Notes */}
          <Route path="/events">
            <Calendar />
          </Route>

          {/* Notes */}
          <Route path="/files">
            <FileWorkspace />
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