import axios from "axios";

const adminUserUrl = () => "http://localhost:8080/admin/users";

export const fetchUsers = () => 
    axios.get(adminUserUrl(), {
        headers : {
            'Authorization' : "Bearer "+ localStorage.getItem("access_token")
        }
});



export const updateUserStatus = (user_id, status) => axios.post(adminUserUrl()+"/"+user_id+"/update",
null,
{
    params : {
        status
    },
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});
