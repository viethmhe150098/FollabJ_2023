
import { useDispatch } from "react-redux";
import { useHistory } from "react-router";
import { Link as RouterLink } from "react-router-dom";
import { loginUser } from "../../Redux/auth/apiRequest";
import '../../style/authen.css'
import FullButton from "../../components/Buttons/FullButton";
import AuthenNavbar from "../../components/Nav/AuthenNavbar";
import { useState, useEffect } from 'react';

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [errors, setErrors] = useState({ username: '', password: '' });
    
    const dispatch = useDispatch();
    const navigate = useHistory();

    // Regex to validate email
  const emailRegex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;

  // Regex to validate password
  const passwordRegex = /^(?=.*\d)(?=.*[a-z])[0-9a-zA-Z]{8,}$/;

  useEffect(() => {
    let usernameError = '';
    let passwordError = '';

    if (!username) {
        usernameError = 'Username is required';
      } else if (!emailRegex.test(username)) {
        usernameError = 'Username must be a valid email address';
      }

    if (!password) {
      passwordError = 'Password is required';
    } else if (!passwordRegex.test(password)) {
      passwordError = 'Password must be at least 8 characters long and contain at least 1 digit';
    }

    setErrors({ username: usernameError, password: passwordError });
  }, [username, password]);

    const handleLogin = (e) => {
        e.preventDefault();

        const newUser = {
            username: username,
            password: password,
        };
        //console.log(newUser);
        loginUser(newUser, dispatch, navigate);
        
    }

    return (
        <>
          <AuthenNavbar />
          <div className="Wrapper">
            <div className="auth-form-container">
              <h2>Login</h2>
              <form className="login-form" onSubmit={handleLogin}>
                <label className="semiBold" htmlFor="email">Email</label>
                <input type="text" className="input" placeholder="Enter your username"
                  onChange={(e) => setUsername(e.target.value)}
                />
                {errors.username && <p className="error-message">{errors.username}</p>}
                <label className="semiBold " htmlFor="password">Password</label>
                <input id="password" name="password" className="marginBot" type="password" placeholder="Enter your password"
                  onChange={(e) => setPassword(e.target.value)}
                />
                {errors.password && <p className="error-message">{errors.password}</p>}
                <FullButton title="Get Started"/>
              </form>
              <RouterLink to="/signup">
                <button className="link-btn">Don't have an account?  <span className="semiBold"> Register here.</span></button>
              </RouterLink>
            </div>
          </div>
        </>
      );
    }
    
    export default Login;