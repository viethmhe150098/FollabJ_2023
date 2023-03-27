import axios from "axios";
import { Result } from "postcss";
import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";

import styled from "styled-components";
import { getUserProfileByUserId, updateUserProfile } from "../../Redux/userProfile/userProfileAction";

const UpdateProfileModal = ({ close }) => {
  const user_id = useSelector((state) => state.auth.login.currentUser.id);
  const oldusername = useSelector((state) => state.userProfile.userInfo.username);
  const oldfullname = useSelector((state) => state.userProfile.userInfo.fullname);
  const oldphonenumber = useSelector((state) => state.userProfile.userInfo.phone_number);

  const [fullname, setFullname] = useState(oldfullname);
  const [username, setUsername] = useState(oldusername);
  const [phone_number, setPhonenumber] = useState(oldphonenumber);

  const dispatch = useDispatch();


  //   const [oldPassword, setOldPassword] = useState("");
  //   const [newPassword, setNewPassword] = useState("");
  //   const [reNewPassword, setReNewPassword] = useState("");

  //   // Regex to validate password
  //   const passwordRegex = /^(?=.*\d)(?=.*[a-z])[0-9a-zA-Z]{8,}$/;


  //   const user_id = useSelector((state) => state.auth.login.currentUser.id);

  //   const handleSubmit = (e) => {
  //     e.preventDefault();

  //     // Check if old password is not empty
  //   if (!oldPassword) {
  //     alert("Please enter your old password.");
  //     return;
  //   }

  //   // Check if new password and re-entered new password match
  //   if (newPassword !== reNewPassword) {
  //     alert("New passwords do not match.");
  //     return;
  //   }

  //   // Check if new password meets regex requirements
  //   if (!passwordRegex.test(newPassword)) {
  //     alert("New password must be at least 8 characters long and contain at least one number and one lowercase letter.");
  //     return;
  //   }
  //     // check backend PasswordDTO to know the name of the field

  //     // const passwordFormData =
  //     // {
  //     const passwordFormData = {
  //       req_u_id: user_id,
  //       old_password: oldPassword,
  //       new_password: newPassword,
  //     };
  //     // }

  //     // using axios.post to send data with access token
  //     //axios.post(url, data, config)
  //     axios.post("http://localhost:8080/user/password/"+user_id, passwordFormData, {
  //         headers : {
  //           'Authorization' : "Bearer "+ localStorage.getItem("access_token")
  //       }
  //       })
  //       .then((response) => {
  //         console.log(response);
  //         // Handle success
  //         close();
  //       })
  //       .catch((error) => {
  //         console.error(error);
  //         // Handle error
  //       });
  //   };
  const handleSubmit = (e) => {
    e.preventDefault();
    const updateDataProfile = {
      u_id: user_id,
      fullname,
      username,
      phone_number
    };
    dispatch(updateUserProfile({
      user_id,
      updateDataProfile
    })).unwrap().then((result) => { dispatch(getUserProfileByUserId(user_id)) });
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
