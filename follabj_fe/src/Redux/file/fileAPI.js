import instance from "../axiosInstance";

const fileURL = (project_id) => "/project/"+project_id+"/files"

export const fetchFiles = (project_id) => instance.get(fileURL(project_id))

export const uploadFile = (project_id, data) => instance.post(fileURL(project_id)+"/upload",data, {
    headers : {
        'Content-Type': 'multipart/form-data'
    }
})

export const downloadFile = (project_id, file_id) => instance.get(fileURL(project_id)+"/download/"+file_id, {
    
    responseType : "blob"
})


