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
                    <h2>Welcome back, [username]!</h2>
                    <button className='darkBg font25 radius6 lightColor tag'>Change password</button>
                    <button onClick={() => handleLogout()} className='redBg font25 radius6 lightColor tag'>Logout</button>
                </div>
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
