import Kanban from "../components/Sections/Kanban";
import Sidebar from "../components/Nav/Sidebar";
import Note from "../components/Sections/Note";
import Meeting from "../screens/Meeting";
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
          <Route path="/task">
            <Kanban />
          </Route>

          {/* Notes */}
          <Route path="/notes">
            <Note />
          </Route>

          {/* Meeting */}
          <Route path="/meeting">
            <Meeting />
          </Route>
          
        </Switch>
      </Sidebar>
    </Router>
  );
}