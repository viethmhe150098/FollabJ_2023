import React, { useState } from "react";
import AuthenNavBar from "../components/Nav/AuthenNavbar"
import styled from "styled-components";
import { useHistory } from "react-router";

const UserProfile = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(true);
    const history = useHistory();
    //handle logout hear
    const handleLogout = () => { 
        
        localStorage.clear();
        setIsLoggedIn(false);
        history.push('/login');

    };
    
    return (
        <>
            <Wrapper>
                <AuthenNavBar />
                <div style={{ margin: "200px" }}>  <h1>User Profile</h1>
                    <p>Welcome back, [username]!</p>
                    <button className="red-btn" onClick={handleLogout}>Logout</button></div>
            </Wrapper>
        </>

    );
};
const Wrapper = styled.div`
margin-top: 50px;
display: flex;
min-height: 100vh;
background-color: #fff;
`

export default UserProfile;
