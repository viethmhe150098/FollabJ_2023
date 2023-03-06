import axios from "axios";
import { async } from "q";
import jwtDecode from 'jwt-decode';
import {loginFailed, loginStart, loginSuccess, registerFailed, registerStart, registerSuccess } from "./authSlice";
import { createProjectFailed, createProjectStart, createProjectSuccess, getProjectStart } from "../project/projectSlice";


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

        //console.log(res.data);
        const access_token = res.data.access_token;
        const refresh_token = res.data.refresh_token;
        localStorage.setItem("access_token", access_token);
        //console.log("access_token ", res.data.access_token);
        localStorage.setItem("refresh_token", refresh_token);
        //console.log("refresh_token", res.data.refresh_token);
        
        localStorage.setItem("expire_date", new Date().getTime() + 10*60*1000)

        const decodedToken = jwtDecode(localStorage.getItem('access_token'));
        //console.log('decoded token '+decodedToken);

        const role_name = decodedToken.roles;
        if (role_name.includes('LEADER')) {
            localStorage.setItem("role_name", role_name);
          }
        //console.log("role_name", role_name);


        //console.log(res)
        dispatch(loginSuccess(res.data));
        // go to home page
        navigate.push("/")
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

// export const getAllProjects = async (access_token,dispatch) =>{
//     dispatch(getProjectStart());
//     try {
//         const res = await axios.get 
//     } catch (error) {
        
//     }
// }

    export const createProject = async(project,access_token,dispatch,navigate) =>{
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
                 navigate.push("/aboutProject");
             } catch (error) {
                dispatch(createProjectFailed());
             }
        }
    }    