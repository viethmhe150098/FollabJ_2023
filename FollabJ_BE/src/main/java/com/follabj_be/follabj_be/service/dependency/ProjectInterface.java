package com.follabj_be.follabj_be.service.dependency;

import com.follabj_be.follabj_be.dto.CreateProjectDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.exception.GroupException;

import java.util.List;
import java.util.Optional;

public interface ProjectInterface {
    Project createPrj(CreateProjectDTO createProjectDTO) throws GroupException;

    void sendInvitation(UserDTO user, Long p_id);

    List<Project> getProjectByUserId(Long u_id);
}
