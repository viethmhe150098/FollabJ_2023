import React from 'react';
import { Route } from 'react-router-dom';
import { isLoggedIn } from '../../Redux/auth/auth';
import Landing from '../../screens/Landing/Landing';

const AuthenciatedRoute = (props) => {
    //console.log(props)
    if(isLoggedIn()) {
        return <Route path={props.path} >
            {props.children}
        </Route>
    } else {
        return <Route path="/" >
            <Landing /> 
        </Route>
    }
  
}

export default AuthenciatedRoute;