// import Kanban from "../../components/Sections/Kanban";
import Sidebar from "../../components/Nav/Sidebar";
import AboutProject from "./About/AboutProject";
// import Note from "../components/Sections/Note";
import MeetingCreation from "../ProjectWorkspace/Meeting/MeetingCreation";
import Calendar from "../ProjectWorkspace/Event/Calendar";
import Note from "./Note/Note";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";
import Kanban from "./Task/Kanban";
import FileWorkspace from "./File/File";
import NoteEditor from "./Note/NoteEditor";
import Chat from "./Chat/Chat";




export default function ProjectManagement() {
  return (
    <>
      <ToastContainer />
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

          {/* Note Edit */}
          <Route exact path="/noteEdit">
            <NoteEditor />
          </Route>

          {/* Event */}
          <Route path="/events">
            <Calendar />
          </Route>

          {/* Files */}
          <Route path="/files">
            <FileWorkspace />
          </Route>

          {/* Chat */}
          <Route path="/chat">
            <Chat />
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
    </>
  );
}