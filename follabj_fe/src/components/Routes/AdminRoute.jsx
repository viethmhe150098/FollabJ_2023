import React from 'react';
import { Route } from 'react-router-dom';
import { isLoggedIn } from '../../Redux/auth/auth';
import Landing from '../../screens/Landing/Landing';

const AdminRoute = (props) => {

    const roles = localStorage.getItem("role_name")
    //console.log(role_name)
    if(isLoggedIn() && roles.includes("ADMIn")) {
        return <Route path={props.path} >
            {props.children}
        </Route>
    } else {
        return <Route path="/" >
            <Landing /> 
        </Route>
    }
}

export default AdminRoute;