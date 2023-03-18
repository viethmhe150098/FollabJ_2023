import AdminSidebar from "../../components/Nav/AdminSidebar";
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";
import Requests from "./RequestManagement/Requests";
import Users from "./UserManagement/Users";



export default function AdminDashboard() {
  return (
    <>
      <AdminSidebar>
        <Switch>
          <Route path="/admin/users">
            <Users />
          </Route>

          <Route path="/admin/requests">
            <Requests/>
          </Route>
        
        </Switch>
      </AdminSidebar>
    </>
  );
}