
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router";
import { Link as RouterLink } from "react-router-dom";
import { loginUser } from "../../Redux/auth/apiRequest";
import '../../style/authen.css'
import FullButton from "../../components/Buttons/FullButton";
import AuthenNavbar from "../../components/Nav/AuthenNavbar";
import { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import { isLoggedIn } from "../../Redux/auth/auth";

const Login = () => {
  const navigate = useHistory();
  if (isLoggedIn()) navigate.push("/");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({ email: '', password: '' });
  const dispatch = useDispatch();


  // Regex to validate email
  const emailRegex = /^\s*([a-zA-Z0-9_.-]{1,60})@([a-zA-Z0-9_.-]+\.[a-zA-Z0-9_.-]+)\s*$/;

  // Regex to validate password
  const passwordRegex = /^(?=.*\d)[a-zA-Z0-9]{8,}$/
  const loginError = useSelector(state => state.auth.login.error);

  useEffect(() => {
    let usernameError = '';
    let passwordError = '';



    if (!email) {
      // usernameError = 'email is required';
    } else if (!emailRegex.test(email)) {
      usernameError = 'Email must be up to 60 characters long and may only contain hyphens, dots, underscores, and alphanumeric characters.'

    }

    if (!password) {
      // passwordError = 'Password is required';
    } else if (!passwordRegex.test(password)) {
      passwordError = 'New password must be 8+ characters, at least 1 digit and do not have special characters or space.';
    }

    setErrors({ email: usernameError, password: passwordError });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [email, password]);


  const handleLogin = (e) => {
    e.preventDefault();
    if ( errors.password !== '' || errors.email !== '') {
      toast.error('Please ensure that all information is accurate and complete!')
      return;
    }
    const newUser = {
      username: email.trim(),
      password: password.trim(),
    };

    loginUser(newUser, dispatch, navigate);
  }


  return (
    <>
      <AuthenNavbar />
      <div className="Wrapper">
        <div className="auth-form-container">
          <h2>Login</h2>
          <form className="login-form" onSubmit={handleLogin}>
            <label className="semiBold font15" htmlFor="email">Email</label>
            <input type="text" className="input" placeholder="Enter your email" required
              onChange={(e) => setEmail(e.target.value)}
            />
            {/* {errors.email && <p className="spanError marginBot">{errors.email}</p>} */}
            {errors.email && (
              <div className="error-wrapper">
                <p className="error-message">{errors.email}</p>
              </div>
            )}
            <label className="semiBold font15 " htmlFor="password">Password</label>
            <input id="password" name="password" type="password" placeholder="Enter your password" required
              onChange={(e) => setPassword(e.target.value)}
            />
            {/* {errors.password && <p className="spanError marginBot">{errors.password}</p>} */}
            {errors.password && (
              <div className="error-wrapper">
                <p className="error-message ">  {errors.password}
                </p>
              </div>
            )}
            <div style={{ marginTop: '10px ' }}></div>
            <FullButton title="Get Started" />
          </form>
          <RouterLink to="/signup">
            <button className="link-btn">Don't have an account?  <span className="semiBold"> Register here.</span></button>
          </RouterLink>
          <RouterLink to="/forgot">
            <button className="link-btn">Forgot password  <span className="semiBold"> Click here.</span></button>
          </RouterLink>
        </div>
      </div>
    </>
  );
}

export default Login;
