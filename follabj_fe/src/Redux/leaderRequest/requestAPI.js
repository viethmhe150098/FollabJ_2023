import instance from "../axiosInstance";

const leaderRequestUrl = () => "http://localhost:8080/admin/request";
const userRequestUrl = () => "/user/request"

export const fetchRequest = (page_number) => 
instance.get(leaderRequestUrl()+"?page="+page_number);

export const getRequestByUserId = () => instance.get(userRequestUrl())

export const updateStatusRequest = (request_id, status) => instance.post(leaderRequestUrl()+"/"+request_id,
    null,
    { params: {
        status
    }
});
