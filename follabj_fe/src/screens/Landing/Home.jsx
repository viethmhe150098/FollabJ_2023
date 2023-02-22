import React from 'react';
import { Redirect } from 'react-router-dom';
import { isLoggedIn } from '../../Redux/auth/auth';

//sreens
import LoggedLanding from './LoggedLanding.jsx';
import NotLoggedLanding from './NotLoggedLanding.jsx';
function Home() {
    if (isLoggedIn()) {
        return (
            <LoggedLanding />
        );
    } else {
        return (
            <NotLoggedLanding />
        );
    }
}

export default Home