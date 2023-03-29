
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router";
import { Link as RouterLink, Navigate } from "react-router-dom";
import { loginUser } from "../../Redux/auth/apiRequest";
import '../../style/authen.css'
import FullButton from "../../components/Buttons/FullButton";
import AuthenNavbar from "../../components/Nav/AuthenNavbar";
import { useState, useEffect } from 'react';
import { toast } from "react-toastify";

const ForgotPassword = () => {



  // Regex to validate email
  const emailRegex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;
  // Regex to validate password

  
  return (
    <>
      <AuthenNavbar />
      <div className="Wrapper">
        <div className="auth-form-container">
          <h2>Set Your Password</h2>
          <form className="login-form">
            <label className="semiBold font15" htmlFor="email">Email</label>
            <input type="text" className="input" placeholder="Enter your username" required/>
            <div style={{ marginTop: '10px ' }}></div>
            <FullButton title="Get Started" />
          </form>
          <RouterLink to="/signup">
            <button className="link-btn">Don't have an account?  <span className="semiBold"> Register here.</span></button>
          </RouterLink>
       
        </div>
      </div>
    </>
  );
}

export default ForgotPassword;
