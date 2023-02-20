import axios from "axios";
import { loginFailed, loginStart, loginSuccess, registerFailed, registerStart, registerSuccess } from "./authSlice";

export const loginUser = async(user,dispatch,navigate) =>{
    dispatch(loginStart());
    try {
        const res = await axios.post("http://localhost:8080/login",
                                        user,
                                        {
                                            headers: {
                                                'Content-Type': 'application/x-www-form-urlencoded'
                                              }
                                        });

        console.log(res.data);
        const accessToken = res.data.access_token;
        localStorage.setItem("accessToken", accessToken);
        console.log(res.data.access_token);

        //console.log(res)
        dispatch(loginSuccess(res.data));
        // go to home page
        navigate.push("/loggedLanding")
    } catch (error) {
        //console.log(error)
        dispatch(loginFailed());
    }
}

export const registerUser = async(user,dispatch,navigate) =>{
    dispatch(registerStart());
    try {
        await axios.post("http://localhost:8080/signup",
                                       user,
                                        {
                                            // headers: {                                            
                                                //     'Content-Type': 'application/x-www-form-urlencoded'
                                            // }
                                        });
        dispatch(registerSuccess());
        navigate.push("/login");
    } catch (error) {
        dispatch(registerFailed());
    }
}