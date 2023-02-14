package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.repository.ProjectRepository;
import com.follabj_be.follabj_be.service.dependency.ProjectInterface;
import org.springframework.stereotype.Service;

@Service
public class ProjectService implements ProjectInterface {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public AppUser getLeaderId(String id) {
        AppUser user = projectRepository.getLeader(id);
        return user;
    }
}
