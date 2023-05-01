import { useState, useEffect } from 'react';
import { useDispatch } from "react-redux";
import { useHistory } from "react-router";
import { registerUser } from "../../Redux/auth/apiRequest";
import { Link as RouterLink } from "react-router-dom";
import '../../style/authen.css'
import FullButton from "../../components/Buttons/FullButton";
import AuthenNavbar from "../../components/Nav/AuthenNavbar"
import { toast } from 'react-toastify';
import { isLoggedIn } from '../../Redux/auth/auth';

const SignUp = () => {

  const navigate = useHistory();
  if (isLoggedIn()) navigate.push("/");

  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const [errors, setErrors] = useState({ username: '', password: '', email: '' });
  const [disableRegister, setDisableRegister] = useState(true);

  const dispatch = useDispatch();

  // Regex to validate email
  const emailRegex = /^\s*([a-zA-Z0-9_.-]{1,60})@([a-zA-Z0-9_.-]+\.[a-zA-Z0-9_.-]+)\s*$/;

  // Regex to validate password
  const PASSWORD_REGEX = /^(?=.*\d)[a-zA-Z0-9]{8,}$/

  // Regex to validate username
  const usernameRegex = /^\s*[a-zA-Z0-9]{5,30}\s*$/

  const handleRegister = (e) => {
    e.preventDefault();
    console.log(errors)
    if(errors.username !=='' || errors.password !== '' || errors.email !== '') {
      toast.error('Please ensure that all registration information is accurate and complete!')
      return;
    }
    const newUser = {
      username: username.trim(),
      email: email.trim(),
      password: password.trim()
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
      emailError = 'Email must be up to 60 characters long and may only contain hyphens, dots, underscores, and alphanumeric characters.'
    }

    if (!username) {
      // usernameError = 'Username is required';
    } else if (!usernameRegex.test(username)) { // Validate username format
      usernameError = 'Username must be between 5 and 30 characters long and must not contain any spaces or special characters.';
    }

    if (!password) {
      // passwordError = 'Password is required';
    } else if (!PASSWORD_REGEX.test(password)) {
      passwordError = 'New password must be 8+ characters, at least 1 digit and do not have special characters or space.';
    }

    setErrors({ email: emailError, username: usernameError, password: passwordError });
    setDisableRegister(emailError || usernameError || passwordError); // Set disableRegister to true if there are any errors
  }, [email, username, password]);


  return (
    <>
      <AuthenNavbar></AuthenNavbar>
      <div className="Wrapper">
        <div className="auth-form-container" style={{ maxHeight: "900px" }}>
          <h2>Register</h2>
          <form className="register-form" onSubmit={handleRegister} >
            <label className="semiBold font15" htmlFor="email">Email</label>
            <input type="text" placeholder="Enter your email" required
              onChange={(e) => setEmail(e.target.value)}
            />
            {/* {errors.email && <p className="error-message">{errors.email}</p>}  */}
            {errors.email && (
              <div className="error-wrapper">
                <p className="error-message">{errors.email}</p>
              </div>
            )}
            <label className="semiBold font15" htmlFor="username">Username</label>
            <input type="text" placeholder="Enter your username" required
              onChange={(e) => setUsername(e.target.value)}
            />
            {/* {errors.username && <p className="error-message">{errors.username}</p>}  */}
            {errors.username && (
              <div className="error-wrapper">
                <p className="error-message">{errors.username}</p>
              </div>
            )}
            <label className="semiBold font15" htmlFor="email">Password</label>
            <input type="password" placeholder="Enter your password" required
              onChange={(e) => setPassword(e.target.value)}
            />
            {/* {errors.password && <p className="error-message">{errors.password}</p>}  */}
            {errors.password && (
              <div className="error-wrapper">
                <p className="error-message">
                  {errors.password}
                </p>
              </div>
            )}
            <div style={{ marginTop: '10px ' }}></div>
            <FullButton title={"Register"} disabled={disableRegister} />

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