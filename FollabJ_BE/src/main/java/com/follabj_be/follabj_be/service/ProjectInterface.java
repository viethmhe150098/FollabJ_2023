package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.dto.CreateProjectDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.exception.GroupException;

import java.util.List;

public interface ProjectInterface {
    Project createPrj(CreateProjectDTO createProjectDTO) throws GroupException;

    void sendInvitation(UserDTO user, Long p_id);

    List<UserDTO> getMembersByProjectId(Long project_id);

    List<Project> getProjectByUserId(Long u_id);

    void deleteProject(Long p_id);

    void editProject(Long p_id, CreateProjectDTO createProjectDTO);

    void deleteMember(Long p_id, Long u_id);
}
