import instance from "../axiosInstance";
const noteUrl = (user_id) => "/notes/"+user_id;

export const fetchNotesByUserId = (user_id) => 
instance.get("/notes/"+user_id);

export const fetchNotesByNoteId = (user_id, note_id) => instance.get(noteUrl(user_id)+"/"+note_id);

export const addNote = (user_id, post) => instance.post(noteUrl(user_id),post);

export const updateNote = (user_id, post) => instance.put(noteUrl(user_id),post);

export const deleteNote = (user_id,note_id) => instance.delete(noteUrl(user_id),{
    params :{
        note_id
    }
});
