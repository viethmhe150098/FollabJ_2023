import axios from "axios";

export const fetchUserProfileByUserId = (user_id) => axios.get("http://localhost:8080/"+user_id,{
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});