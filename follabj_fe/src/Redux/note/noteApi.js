import instance from "../axiosInstance";
const noteUrl = () => "/notes";

export const fetchNotesByUserId = () => 
instance.get("/notes");

export const fetchNotesByNoteId = (note_id) => instance.get(noteUrl()+"/"+note_id);

export const addNote = (post) => instance.post(noteUrl(),post);

export const updateNote = (post) => instance.put(noteUrl()+"/"+post.id,post);

export const deleteNote = (note_id) => instance.delete(noteUrl()+"/"+ note_id);
