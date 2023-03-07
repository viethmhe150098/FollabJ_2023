import axios from "axios";

const noteUrl = (user_id) => "http://localhost:8080/notes/"+user_id;

export const fetchNotesByUserId = (user_id) => 
    axios.get("http://localhost:8080/notes/"+user_id, {
        headers : {
            'Authorization' : "Bearer "+ localStorage.getItem("access_token")
        }
});

export const fetchNotesByNoteId = (user_id, note_id) => axios.get(noteUrl(user_id)+"/"+note_id,{
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});

export const addNote = (user_id, post) => axios.post(noteUrl(user_id),post,{
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});

export const updateNote = (user_id, post) => axios.put(noteUrl(user_id),post,{
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});

export const deleteNote = (user_id) => axios.delete(noteUrl(user_id),{
    headers : {
        'Authorization' : "Bearer "+ localStorage.getItem("access_token")
    }
});