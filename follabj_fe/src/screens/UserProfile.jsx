import React, { useState } from "react";
import AuthenNavBar from "../components/Nav/AuthenNavbar"
import styled from "styled-components";


const UserProfile = () => {


    //handle logout hear
    const handleLogout = () => { };

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
