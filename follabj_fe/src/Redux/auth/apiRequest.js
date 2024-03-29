import axios from "axios";
import jwtDecode from 'jwt-decode';
import { loginFailed, loginStart, loginSuccess, registerFailed, registerStart, registerSuccess } from "./authSlice";
import { createProjectFailed, createProjectStart, createProjectSuccess, getProjectStart, setCurrentProjectId } from "../project/projectSlice";
import { toast } from "react-toastify";
import { getProjectMembersByProjectId } from "../project/projectActions";

export const loginUser = async (user, dispatch, navigate) => {
    dispatch(loginStart());
    try {
        const res = await axios.post("http://localhost:8080/login",
            user,
            {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });

        //console.log(res.data);
        const access_token = res.data.access_token;
        const refresh_token = res.data.refresh_token;
        localStorage.setItem("access_token", access_token);
        //console.log("access_token ", res.data.access_token);
        localStorage.setItem("refresh_token", refresh_token);
        //console.log("refresh_token", res.data.refresh_token);

        localStorage.setItem("expire_date", new Date().getTime() + 60 * 60 * 1000)

        const decodedToken = jwtDecode(localStorage.getItem('access_token'));
        //console.log('decoded access token '+JSON.stringify(decodedToken));

        // const decodedRefreshToken = jwtDecode(localStorage.getItem('refresh_token'));
        // console.log('decoded refresh token '+JSON.stringify(decodedRefreshToken));

        const role_name = decodedToken.roles;

        localStorage.setItem("role_name", role_name);

        console.log("role_name", role_name);



        //console.log(res)
        if (res.data.status === "1") {
            dispatch(loginSuccess(res.data));
            toast.success('Welcome back! 🧡')
            navigate.push("/")
        } else if (res.data.status === "0") {
            toast.info("You need to active your account first with the code sent to your email address!")
            localStorage.clear()
        } else {
            toast.error("You have been banned!")
            localStorage.clear()
        }
        if (role_name.includes("ADMIN")) {
            navigate.push("/admin/dashboard")
        }
    } catch (error) {
        //console.log(error)
        toast.error('Invalid username or password')

    }
}

export const registerUser = async (user, dispatch, navigate) => {
    dispatch(registerStart());
    
    try {
        toast.info('Loading your information. This may take a few moments.');
        await axios.post("http://localhost:8080/signup",
            user,
            {
                // headers: {                                            
                //     'Content-Type': 'application/x-www-form-urlencoded'
                // }
            });
       
        dispatch(registerSuccess());
        toast.dismiss();
        toast.success('Register successfully, please CHECK your email!', {
            autoClose: 10000
        })
        navigate.push("/login");
    } catch (error) {
        dispatch(registerFailed());
        toast.dismiss();
        toast.error(error.response.data.message);
    }
}


export const createProject = async (project, access_token, dispatch, navigate) => {
    dispatch(createProjectStart());

    const role_name = localStorage.getItem("role_name");
    if (role_name.includes('LEADER')) {
        try {
            const res = await axios.post("http://localhost:8080/createproject",
                project,
                {
                    headers: {
                        Authorization: `Bearer ${access_token}`
                    }
                });
            dispatch(createProjectSuccess(res.data));
            toast.success('Create project successfully!')
            dispatch(setCurrentProjectId(res.data.id))
            dispatch(getProjectMembersByProjectId(res.data.id))
            navigate.push("/aboutProject");
        } catch (error) {
            dispatch(createProjectFailed());
            toast.error(error.response.data.message);

        }
    }
}    