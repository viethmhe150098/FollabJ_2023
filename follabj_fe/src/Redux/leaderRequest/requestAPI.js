import axios from "axios";

const leaderRequestUrl = () => "http://localhost:8080/admin/request";

export const fetchRequest = (page_number) => 
    axios.get(leaderRequestUrl()+"?page="+page_number, {
        headers : {
            'Authorization' : "Bearer "+ localStorage.getItem("access_token")
        }
});



export const updateStatusRequest = (request_id, status) => axios.post(leaderRequestUrl()+"/"+request_id,
    null,
    { params: {
        status
    },
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});
