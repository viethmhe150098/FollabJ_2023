import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { useHistory } from "react-router";
import Popup from "reactjs-popup";
import ChangePasswordModal from "../components/Modals/ChangePasswordModal";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { getUserProfileByUserId } from "../Redux/userProfile/userProfileAction";
import UpdateProfileModal from "../components/Modals/UpdateProfile";
import TopNavbar from "../components/Nav/TopNavbar";

const UserProfile = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(true);
    const history = useHistory();
    const dispatch = useDispatch();

    const user_id = useSelector((state) => state.auth.login.currentUser.id);

    const userProfile = useSelector((state) => state.userProfile.userInfo);

    useEffect(() => {
        dispatch(getUserProfileByUserId(user_id));
    }, []);

    // handle logout hear
    const handleLogout = () => {
        localStorage.clear();
        setIsLoggedIn(false);
        history.push("/login");
    };

    return (
        <>
            <TopNavbar />
            <UserProfileContainer>
                <UserProfileHeader>User Profile</UserProfileHeader>
                <UserInformation>
                    <Label>Email:</Label>
                    <UserInfo>{userProfile?.email}</UserInfo>
                </UserInformation>
                <UserInformation>
                    <Label>Username:</Label>
                    <UserInfo>{userProfile?.username}</UserInfo>
                </UserInformation>
                {/* <UserInformation>
                    <Label>ID:</Label>
                    <UserInfo>{userProfile?.id}</UserInfo>
                </UserInformation> */}
                <UserInformation>
                    <Label>Fullname:</Label>
                    <UserInfo>{userProfile?.fullname}</UserInfo>
                </UserInformation>
                <UserInformation>
                    <Label>Phone number:</Label>
                    <UserInfo>{userProfile?.phone_number}</UserInfo>
                </UserInformation>
                <div style={{ display: 'inline-flex' }}>
                    <Popup modal trigger={
                        <ChangePasswordButton className="animate">
                            Change password
                        </ChangePasswordButton>
                    }
                    >
                        {(close) => <ChangePasswordModal close={close} />}
                    </Popup>

                    <Popup modal trigger={
                        <UpdateButton className="animate">
                            Update profile
                        </UpdateButton>
                    }
                    >
                        {(close) => <UpdateProfileModal close={close} />}
                    </Popup>

                    <LogoutButton className="animate" onClick={() => handleLogout()}>
                        Logout
                    </LogoutButton>
                </div>
            </UserProfileContainer>
        </>
    );
};

const UserProfileContainer = styled.div`
  margin: 200px 100px;
  padding: 40px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);
`;

const UserProfileHeader = styled.h1`
  font-size: 36px;
  font-weight: 700;
  margin-bottom: 40px;
`;

const UserInformation = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 20px;
`;

const Label = styled.span`
  font-size: 18px;
  font-weight: 700;
  margin-right: 20px;
`;

const UserInfo = styled.span`
  font-size: 18px;
  font-weight: 400;
`;

const Button = styled.button`
  display: block;
  min-width: 200px;
  margin: 50px 45px 50px 0;
  padding: 10px 20px;
  border-radius: 6px;
  font-size: 18px;
  font-weight: 700;
  cursor: pointer;
`;

const UpdateButton = styled(Button)`
  background: rgb(145 254 159 / 47%);
  color: green;
  
  &:hover {
    background: green;
    color: white;
  }
`;
const ChangePasswordButton = styled(Button)`
  background: #59bfff;
  color: white;
  &:hover {
    background-color: #007fff;
    color: white;
  }
`;

const LogoutButton = styled(Button)`
  background: #ffadad8f;
  color: red;
  &:hover {
    background-color: #ff0000;
    color: white;
  }
`;
export default UserProfile;
