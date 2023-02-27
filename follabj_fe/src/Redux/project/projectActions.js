import * as projectAPI from "./projectAPI";

export const getProjectsByUserId = createAsyncThunk("FETCH_PROJECTS_BY_USER_ID", async (user_id) => {
    try {
        const response = await projectAPI.fetchProjectsByUserId(user_id)
        //console.log(response)
        return response.data
    } catch (error) {
        console.log(error);
    }
})