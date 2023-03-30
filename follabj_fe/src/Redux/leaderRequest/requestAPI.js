import instance from "../axiosInstance";

const leaderRequestUrl = () => "http://localhost:8080/admin/request";
const userRequestUrl = (user_id) => "/user/"+user_id+"/request"

export const fetchRequest = (page_number) => 
instance.get(leaderRequestUrl()+"?page="+page_number);

export const getRequestByUserId = (user_id) => instance.get(userRequestUrl(user_id))

export const updateStatusRequest = (request_id, status) => instance.post(leaderRequestUrl()+"/"+request_id,
    null,
    { params: {
        status
    }
});
