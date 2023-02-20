import { useState } from "react";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router";
import { Link as RouterLink } from "react-router-dom";
import { loginUser } from "../../Redux/actions/apiRequest";
import '../../style/authen.css'
import FullButton from "../../components/Buttons/FullButton";
import AuthenNavbar from "../../components/Nav/AuthenNavbar"

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const dispatch = useDispatch();
    const navigate = useHistory();

    const handleLogin = (e) => {
        e.preventDefault();
        const newUser = {
            username: username,
            password: password,
        };
        //console.log(newUser);
        loginUser(newUser, dispatch, navigate);

    }

    return (<>
        <AuthenNavbar></AuthenNavbar>
        <div className="Wrapper" >
            <div className="auth-form-container">
                <h2>Login</h2>
                <form className="login-form" onSubmit={handleLogin} >
                    <label className="semiBold" htmlFor="email">Email</label>
                    <input type="text" className="input" placeholder="Enter your username"
                        onChange={(e) => setUsername(e.target.value)}
                    />
                    <label className="semiBold " htmlFor="password">Password</label>
                    <input id="password" name="password" className="marginBot" type="password" placeholder="Enter your password"
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    {/* <button type="submit"> Continue </button> */}
                    <FullButton title="Get Started" action={"submit"} />
                </form>
                <RouterLink to="/register">
                    <button className="link-btn " >Don't have an account?  <span className="semiBold"> Register here.</span></button>

                </RouterLink>
                {/* <Link className="login-register-link" to="/signup">Register one for free </Link> */}

            </div>
        </div>
    </>
    );
}

export default Login;