import React, { useEffect, useState } from "react";
import AuthenNavBar from "../components/Nav/AuthenNavbar"
import styled from "styled-components";
import { useHistory } from "react-router";
import Popup from "reactjs-popup";
import ChangePasswordModal from "../components/Modals/ChangePasswordModal";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { getUserProfileByUserId } from "../Redux/userProfile/userProfileAction";
import UpdateProfileModal from "../components/Modals/UpdateProfile";

const UserProfile = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(true);
    const history = useHistory();
    const dispatch = useDispatch();

    const user_id = useSelector((state) => state.auth.login.currentUser.id)

    const userProfile = useSelector((state) => state.userProfile.userInfo)

    useEffect(()=>{
        dispatch(getUserProfileByUserId(user_id))
    },[])


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
                <div style={{ margin: "200px" }}> 
                            <h1>User Profile</h1>
                            { (userProfile!=null) && 
                            (<>
                                <h1>{userProfile.id}</h1>
                                <h1>{userProfile.email}</h1>
                                <h1>{userProfile.username}</h1>
                            </>)}
                           
                    <Popup modal trigger={ <button className='darkBg font25 radius6 lightColor tag'>Change password</button>}>
                        {close => <ChangePasswordModal close={close}/>}
                    </Popup>
                    <Popup modal trigger={ <button className='darkBg font25 radius6 lightColor tag'>Update profile</button>}>
                        {close => <UpdateProfileModal close={close}/>}
                    </Popup>
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
