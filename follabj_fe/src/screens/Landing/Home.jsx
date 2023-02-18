import React from 'react';
import { Redirect } from 'react-router-dom';
import { isLoggedIn } from './auth';

//sreens
import LoggedLanding from LoggedLanding;
import NotLoggedLanding from NotLoggedLanding;
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