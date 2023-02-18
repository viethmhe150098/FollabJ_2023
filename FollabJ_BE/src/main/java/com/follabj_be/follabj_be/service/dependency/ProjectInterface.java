package com.follabj_be.follabj_be.service.dependency;

import com.follabj_be.follabj_be.dto.CreateProjectDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.exception.GroupException;

public interface ProjectInterface {
    Project createPrj(CreateProjectDTO createProjectDTO) throws GroupException;
}