import instance from "../axiosInstance";

const leaderRequestUrl = () => "http://localhost:8080/admin/request";
const userRequestUrl = () => "/user/request"

export const fetchRequest = () => 
instance.get(leaderRequestUrl());

export const getRequestByUserId = () => instance.get(userRequestUrl())

export const updateStatusRequest = (request_id, status) => instance.post(leaderRequestUrl()+"/"+request_id,
    null,
    { params: {
        status
    }
});
