import { useState } from "react";
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
    const dispatch = useDispatch();
    const navigate = useHistory();

    const handleRegister = (e) => {
        e.preventDefault();
        const newUser = {
            username: username,
            email: email,
            password: password
        };
        registerUser(newUser, dispatch, navigate);

    }
    return (
        <>
            <AuthenNavbar></AuthenNavbar>
            <div className="Wrapper">
                <div className="auth-form-container"  style={{maxHeight: "900px"}}>
                    <h2>Register</h2>
                    <form className="register-form" onSubmit={handleRegister} >
                    <label className="semiBold" htmlFor="email">Email</label>
                        <input type="text" placeholder="Enter your email"
                            onChange={(e) => setEmail(e.target.value)}
                        />
                       <label className="semiBold" htmlFor="email">Username</label>
                        <input type="text" placeholder="Enter your username"
                            onChange={(e) => setUsername(e.target.value)}
                        />
                        <label className="semiBold" htmlFor="email">Password</label>
                        <input type="password" placeholder="Enter your password"
                            onChange={(e) => setPassword(e.target.value)}
                        />
                        {/* <button type="submit"> Create account </button> */}
                        <div className="marginBot"></div>
                        <FullButton title={"Register"}  />
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