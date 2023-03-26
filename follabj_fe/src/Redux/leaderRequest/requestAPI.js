import instance from "../axiosInstance";

const leaderRequestUrl = () => "http://localhost:8080/admin/request";

export const fetchRequest = (page_number) => 
instance.get(leaderRequestUrl()+"?page="+page_number);


export const updateStatusRequest = (request_id, status) => instance.post(leaderRequestUrl()+"/"+request_id,
    null,
    { params: {
        status
    }
});
