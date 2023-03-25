import instance from "../axiosInstance";

const adminUserUrl = () => "/admin/users";

export const fetchUsers = () => 
instance.get(adminUserUrl());



export const updateUserStatus = (user_id, status) => instance.post(adminUserUrl()+"/"+user_id+"/update",
null,
{
    params : {
        status
    }
});
