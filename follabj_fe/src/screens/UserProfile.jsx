import React, { useState,useEffect } from "react";
import AuthenNavBar from "../components/Nav/AuthenNavbar"
import styled from "styled-components";
import { useHistory } from "react-router";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { getUserProfileByUserId } from "../Redux/userProfile/userProfileAction";


const UserProfile = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(true);
    const history = useHistory();
    const dispatch = useDispatch();

    const user_id = useSelector((state) => state.auth.login.currentUser.id)

    const userProfile = useSelector((state) => state.userProfile)

    useEffect(()=>{
      dispatch(getUserProfileByUserId(user_id))
    },[userProfile])
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
                <p>Name: {userProfile.username}</p>
                <p>Email: {userProfile.email}</p>
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
