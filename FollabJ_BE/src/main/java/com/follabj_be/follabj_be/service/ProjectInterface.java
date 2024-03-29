package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.dto.CreateProjectDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.exception.GroupException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectInterface {
    Project createPrj(CreateProjectDTO createProjectDTO) throws GroupException;

    String sendInvitation(UserDTO user, Long p_id);

    List<UserDTO> getMembersByProjectId(Long project_id);

    List<Project> getProjectByUserId(Long u_id);

    void deleteProject(Long p_id);

    void editProject(Long p_id, CreateProjectDTO createProjectDTO);

    Project addMember(Long p_id, Long u_id);

    void deleteMember(Long p_id, Long u_id);

    void dactivateProject(Long p_id);

    String count(String by);

    List<Project> getAll();
}
