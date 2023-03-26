import axios from "axios";
import { Result } from "postcss";
import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";

import styled from "styled-components";
import { getUserProfileByUserId, updateUserProfile } from "../../Redux/userProfile/userProfileAction";

const UpdateProfileModal = ({ close }) => {
    const user_id = useSelector((state)=>state.auth.login.currentUser.id);
    const oldusername = useSelector((state)=>state.userProfile.userInfo.username);

    const [fullname,setFullname] = useState("")
    const [username,setUsername] = useState(oldusername);
    const [phone_number,setPhonenumber] = useState("");
    
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
    const handleSubmit =(e) =>{
        e.preventDefault();
        const updateDataProfile = {
            u_id : user_id,
            fullname,
            username, 
            phone_number
        };
        dispatch(updateUserProfile({
            user_id,
            updateDataProfile
        })).unwrap().then((result)=>{dispatch(getUserProfileByUserId(user_id))});
        close();
    }

  return (
    <>
      <Modal>
        <a className="close" onClick={close}>
          &times;
        </a>
        <h2>Change Password</h2>

        <form
          id="taskForm"
          onSubmit={(e) => {
            handleSubmit(e);
          }}
        >
          <div className="form-group">
            <label htmlFor="fullname">Full name </label>
            <input
              type="text"
              id="fullname"
              value={fullname}
              onChange={(e) => {
                setFullname(e.target.value);
              }}
            ></input>
          </div>
          <div className="form-group">
            <label htmlFor="username">User name </label>
            <input
              type="text"
              id="username"
              value={username}
              onChange={(e) => {
                setUsername(e.target.value);
              }}
            ></input>
          </div>
          <div className="form-group">
            <label htmlFor="phone_number">Phone number</label>
            <input
              type="text"
              id="phone_number"
              value={phone_number}
              onChange={(e) => {
                setPhonenumber(e.target.value);
              }}
            />
          </div>
          <button
            className="greenBg font25 radius6 lightColor tag"
          >
            Change
          </button>
        </form>
      </Modal>
    </>
  );
};

const Modal = styled.div`
  background-color: #fff;
  padding: 1rem;
  border-radius: 5px;
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);
  height: 100%;
  h2 {
    font-size: 1.5rem;
    margin-bottom: 1.5rem;
  }
  form {
    .form-group {
      margin-bottom: 1rem;
      label {
        font-weight: bold;
        margin-bottom: 0.5rem;
      }
      input,
      textarea {
        padding: 0.5rem;
        border-radius: 5px;
        border: solid black 1px;
        margin: 0;
        &:focus {
          outline: none;
          box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
        }
        .date-picking {
          display: inline-block;
          witdth: 50 %;
          background-color: orange;
          text-color: white;
        }
      }
      textarea {
        border: 1px black solid;
        resize: none;
      }
      select {
        border: 1px black solid;

        padding: 0.5rem;
        border-radius: 5px;
        border: none;
        margin-bottom: 0.5rem;
        &:focus {
          outline: none;
          box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
        }
      }
      label {
        font-weight: bold;
        margin-bottom: 0.5rem;
      }
      input[type="checkbox"] {
        border: 1px black solid;

        margin-right: 0.5rem;
      }
      label {
      }
      #start-date,
      #end-date {
        border: 1px black solid;
        width: 40%;
        &:focus {
          outline: none;
          box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
        }
      }
    }
    button[type="submit"] {
      background-color: orange;
      color: #fff;
      padding: 0.5rem 1rem;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      &:hover {
        background-color: #ff9900;
      }
    }
  }
`;

export default UpdateProfileModal;
