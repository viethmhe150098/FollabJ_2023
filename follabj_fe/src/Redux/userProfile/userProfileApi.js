import instance from "../axiosInstance";
import axios from "axios";


export const fetchUserProfileByUserId = (user_id) => instance.get("/user/"+user_id);

export const updateUserProfile = (user_id,updateDataProfile) => instance.post("/user/info/"+user_id,updateDataProfile)
