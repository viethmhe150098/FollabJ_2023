import axios from "axios";

const userProfileUrl = (user_id) =>"http://localhost:8080/user/"+user_id;

export const fetchUserProfileByUserId = (user_id) => axios.get("http://localhost:8080/user/"+user_id,{
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});