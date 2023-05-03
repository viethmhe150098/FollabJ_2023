import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";

import styled from "styled-components";
import { getUserProfileByUserId, updateUserProfile } from "../../Redux/userProfile/userProfileAction";

const UpdateProfileModal = ({ close }) => {
  const user_id = useSelector((state) => state.auth.login.currentUser.id);
  const oldusername = useSelector((state) => state.userProfile.userInfo.username);
  const oldfullname = useSelector((state) => state.userProfile.userInfo.fullname);
  const oldphonenumber = useSelector((state) => state.userProfile.userInfo.phone_number);

  const [fullname, setFullname] = useState(oldfullname || "");
  const [username, setUsername] = useState(oldusername );
  const [phone_number, setPhonenumber] = useState(oldphonenumber || "");

  const dispatch = useDispatch();

  //   // Regex to validate password
  const phoneRegex = /^0\d{9}$/
  const usernameRegex = /^\s*[a-zA-Z0-9]{5,30}\s*$/
  const fullnameRegex = /^\s*[a-zA-Z]{2,}(?:\s+[a-zA-Z]+){1,4}\s*$/
  const handleSubmit = (e) => {
    e.preventDefault();
    if (fullname === oldfullname && username === oldusername && phone_number === oldphonenumber) {
      toast.warn('No changes were made to your information.')
      return;
    }
    if (!fullnameRegex.test(fullname)) {
      toast.warn("Fullname must be between 5 and 30 characters long and must not contain any special characters.");
      return;
    }
    if (!usernameRegex.test(username)) {
      toast.warn("Username must be between 5 and 30 characters long and must not contain any spaces or special characters.");
      return;
    }
    if (!phoneRegex.test(phone_number)) {
      toast.warn("Phone number must have 10 numerics and start with '0'");
      return;
    }
    const updateDataProfile = {
      u_id: user_id,
      fullname: fullname.trim(),
      username: username.trim(),
      phone_number: phone_number.trim()
    };
    dispatch(updateUserProfile({
      user_id,
      updateDataProfile
    }))
      .unwrap()
      .then((result) => { dispatch(getUserProfileByUserId(user_id)) })
      .catch((error) => {});
    close();
  }

  return (
    <>
      <ModalWrapper>
        <ModalHeader>Update Profile</ModalHeader>
        <CloseButton onClick={close}>&times;</CloseButton>
        <Form onSubmit={(e) => {
          handleSubmit(e);
        }}>
          <FormGroup>
            <Label htmlFor="oldPassword">Full Name</Label>
            <Input
              id="oldPassword"
              value={fullname}
              onChange={(e) => {
                setFullname(e.target.value);
              }}
            />
          </FormGroup>
          <FormGroup>
            <Label htmlFor="newPassword">User Name</Label>
            <Input
              id="newPassword"
              value={username}
              onChange={(e) => {
                setUsername(e.target.value);
              }}
            />
          </FormGroup>
          <FormGroup>
            <Label htmlFor="reNewPassword">Phone Number</Label>
            <Input
              id="reNewPassword"
              value={phone_number}
              onChange={(e) => {
                setPhonenumber(e.target.value);
              }}
            />
          </FormGroup>
          <SubmitButton type="submit">Change</SubmitButton>
        </Form>
      </ModalWrapper>
    </>
  );
};
const ModalWrapper = styled.div`
background-color: #fff;
border-radius: 8px;
box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
padding: 24px;
width: 400px;
max-width: 90%;
position: relative;
margin: 0 auto;
margin-top: 40px;
overflow: hidden;
`;

const ModalHeader = styled.h2`
font-size: 28px;
font-weight: 600;
margin-top: 0;
margin-bottom: 24px;
`;

const Form = styled.form`
display: flex;
flex-direction: column;
`;

const FormGroup = styled.div`
display: flex;
flex-direction: column;
margin-bottom: 24px;
`;

const Label = styled.label`
font-size: 16px;
font-weight: 600;
margin-bottom: 8px;
`;

const Input = styled.input`
border: 1px solid #ccc;
border-radius: 4px;
padding: 12px;
font-size: 16px;
margin-bottom: 8px;
&:focus {
  outline: none;
  box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.3);
}
`;

const SubmitButton = styled.button`
background: rgb(145 254 159 / 47%);
color: green;
border: none;
border-radius: 4px;
padding: 12px;
font-size: 16px;
font-weight: 600;
cursor: pointer;
transition: background-color 0.2s ease-in-out;
&:hover {
  background: green;
  color: white;
}
`;

const CloseButton = styled.button`
position: absolute;
top: 0;
right: 0;
padding: 12px;
background-color: transparent;
border: none;
font-size: 24px;
color: #ccc;
cursor: pointer;
`;
export default UpdateProfileModal;
