
import { Link as RouterLink } from "react-router-dom";
import '../../style/authen.css'
import FullButton from "../../components/Buttons/FullButton";
import AuthenNavbar from "../../components/Nav/AuthenNavbar";
import { useState, useEffect } from 'react';
import { toast } from "react-toastify";
import axios from "axios";

const ForgotPassword = () => {

  const [email, setEmail] = useState('');


  // Regex to validate email
  const emailRegex = /^\s*([a-zA-Z0-9_.-]{1,60})@([a-zA-Z0-9_.-]+\.[a-zA-Z0-9_.-]+)\s*$/;

  const [errors, setErrors] = useState({ email: '' });

  useEffect(() => {
    let emailError = '';
    if (!email) {
      // emailError = 'Email is required';
    } else if (!emailRegex.test(email)) { // Validate email format
      emailError = 'Email must be up to 60 characters long and may only contain hyphens, dots, underscores, and alphanumeric characters.'

    }
    setErrors({ email: emailError });
  }, [email,]);
  const handleSubmit = (e) => {

    e.preventDefault();
    axios
      .post("http://localhost:8080/user/forgot", { email: email.trim() })
      .then((response) => {
        toast.success(response.data.message);
      })
      .catch((error) => {
        console.log(error.response.data);
        toast.error(error.response.data.message);
      });
  }

  return (
    <>
      <AuthenNavbar />
      <div className="Wrapper">
        <div className="auth-form-container">
          <h2>Set Your Password</h2>
          <form className="login-form" onSubmit={handleSubmit}>
            <label className="semiBold font15" htmlFor="email">Email</label>
            <input type="text" className="input" placeholder="Enter your username" required
              onChange={(e) => setEmail(e.target.value)}
            />
            {errors.email && (
              <div className="error-wrapper">
                <p className="error-message">{errors.email}</p>
              </div>
            )}
            <div style={{ marginTop: '10px ' }}></div>
            <FullButton title="Get Started" />
          </form>
          <RouterLink to="/login">
            <button className="link-btn">Do you have your password?  <span className="semiBold"> Login here.</span></button>
          </RouterLink>

        </div>
      </div>
    </>
  );
}

export default ForgotPassword;
