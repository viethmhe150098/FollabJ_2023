import instance from "../axiosInstance";

const projectUrl =(project_id) => "/project/"+project_id;
const adminUrl = () => "/admin"

export const fetchAllProjects = () => instance.get("/admin/getprj")

export const getProjectStatistics = () => instance.get(adminUrl()+"/cp")

export const fetchProjectsByUserId = (user_id) => instance.get("/"+user_id);

export const fetchProjectMemberByProjectId = (project_id) => instance.get(projectUrl(project_id)+"/members")
 
export const inviteMember = (project_id, userdto) => instance.post(projectUrl(project_id)+"/addmembers/leader" , userdto)

export const createProject = (project) => instance.post("/createproject", project)

export const updateProject = (project_id,project) => instance.put(projectUrl(project_id)+"/leader", project)

export const deleteProject = (project_id) => instance.delete(projectUrl(project_id)+"/leader")

export const leaveProject = (project_id,u_id) => instance.post(projectUrl(project_id)+"/leave", null, {
    params: {
        u_id
    }
})

export const assignLeader = (project_id,u_id) => instance.post(projectUrl(project_id)+"/assign", null, {
    params: {
        u_id
    }
})

export const deleteMember= (project_id,member_id) => instance.put(projectUrl(project_id)+"/leader/member/"+member_id)

export const deactiveProject = (project_id) => instance.post(projectUrl(project_id)+"/leader/deactive")