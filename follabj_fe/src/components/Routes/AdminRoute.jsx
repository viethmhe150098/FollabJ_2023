import React from 'react';
import { Route } from 'react-router-dom';
import { isLoggedIn } from '../../Redux/auth/auth';
import Landing from '../../screens/Landing/Landing';
import { toast } from "react-toastify";

const AdminRoute = (props) => {

    const roles = localStorage.getItem("role_name")
    //console.log(role_name)
    if(isLoggedIn() && roles.includes("ADMIN")) {
        return <Route path={props.path} >
            {props.children}
        </Route>
    } else {
        toast.error("You don't have permission to access ADMIN's features!");
        return <Route path="/" >
            <Landing /> 
        </Route>
    }
}

export default AdminRoute;