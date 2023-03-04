import axios from "axios";

const fileURL = (project_id) => "http://localhost:8080/project/"+project_id

export const fetchFiles = (project_id, page_number) => axios.get(fileURL(project_id)+"?page="+page_number, {
    headers : {
        'Authorization': "Bearer "+ localStorage.getItem("access_token"),
    }
})

export const uploadFile = (project_id, data) => axios.post(fileURL(project_id)+"/upload",data, {
    headers : {
        'Authorization': "Bearer "+ localStorage.getItem("access_token"),
        'Content-Type': 'multipart/form-data'
    }
})

export const downloadFile = (project_id, file_id) => axios.get(fileURL(project_id)+"/download/"+file_id, {
    headers : {
        'Authorization': "Bearer "+ localStorage.getItem("access_token")
    },
    responseType : "blob"
})


