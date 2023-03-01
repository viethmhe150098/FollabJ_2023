import { useState, useEffect } from 'react';
import { useDispatch } from "react-redux";
import { useHistory } from "react-router";
import { registerUser } from "../../Redux/auth/apiRequest";
import { Link as RouterLink } from "react-router-dom";
import '../../style/authen.css'
import FullButton from "../../components/Buttons/FullButton";
import AuthenNavbar from "../../components/Nav/AuthenNavbar"
const SignUp = () => {
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const [errors, setErrors] = useState({ username: '', password: '', email: '' });

  const dispatch = useDispatch();
  const navigate = useHistory();

  // Regex to validate email
  const emailRegex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;

  // Regex to validate password
  const passwordRegex = /^(?=.*\d)(?=.*[a-z])[0-9a-zA-Z]{8,}$/;

  // Regex to validate username
  const usernameRegex = /^[a-zA-Z0-9]+([_-]?[a-zA-Z0-9])*$/;

  const handleRegister = (e) => {
    e.preventDefault();
    const newUser = {
      username: username,
      email: email,
      password: password
    };
    registerUser(newUser, dispatch, navigate);

  }

  useEffect(() => {
    let emailError = '';
    let usernameError = '';
    let passwordError = '';

    if (!email) {
      // emailError = 'Email is required';
    } else if (!emailRegex.test(email)) { // Validate email format
      emailError = 'That email does not look quite right';
    }

    if (!username) {
      // usernameError = 'Username is required';
    } else if (!usernameRegex.test(username)) { // Validate username format
      usernameError = 'Username cannot contain special characters';
    }

    if (!password) {
      // passwordError = 'Password is required';
    } else if (!passwordRegex.test(password)) {
      passwordError = 'Must be 8+ characters and at least 1 digit';
    }

    setErrors({ email: emailError, username: usernameError, password: passwordError });
  }, [email, username, password]);

  return (
    <>
      <AuthenNavbar></AuthenNavbar>
      <div className="Wrapper">
        <div className="auth-form-container" style={{ maxHeight: "900px" }}>
          <h2>Register</h2>
          <form className="register-form" onSubmit={handleRegister} >
            <label className="semiBold" htmlFor="email">Email</label>
            <input type="text" placeholder="Enter your email" required
              onChange={(e) => setEmail(e.target.value)}
            />
            {/* {errors.email && <p className="error-message">{errors.email}</p>}  */}
            {errors.email && (
              <div className="error-wrapper">
                <p className="error-message">{errors.email}</p>
                <span className="error-icon" role="img" aria-label="Error icon">❌</span>
              </div>
            )}
            <label className="semiBold" htmlFor="email">Username</label>
            <input type="text" placeholder="Enter your username" required
              onChange={(e) => setUsername(e.target.value)}
            />
            {/* {errors.username && <p className="error-message">{errors.username}</p>}  */}
            {errors.username && (
              <div className="error-wrapper">
                <p className="error-message">{errors.username}</p>
                <span className="error-icon" role="img" aria-label="Error icon">❌</span>
              </div>
            )}
            <label className="semiBold" htmlFor="email">Password</label>
            <input type="password" placeholder="Enter your password" required
              onChange={(e) => setPassword(e.target.value)}
            />
            {/* {errors.password && <p className="error-message">{errors.password}</p>}  */}
            {errors.password && (
              <div className="error-wrapper">
                <p className="error-message">
                  {errors.password}
                </p>
                <span className="error-icon" role="img" aria-label="Error icon">❌</span>
              </div>
            )}
            <FullButton title={"Register"} />
          </form>
          <RouterLink to="/login">
            <button className="link-btn">Already have an account? <span className="semiBold"> Login here.</span></button>
          </RouterLink>
        </div>
      </div>
    </>

  );
}

export default SignUp;