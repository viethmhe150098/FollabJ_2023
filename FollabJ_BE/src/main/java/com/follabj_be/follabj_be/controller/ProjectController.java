package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.CreateProjectDTO;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.exception.GroupException;
import com.follabj_be.follabj_be.service.ProjectService;
import com.follabj_be.follabj_be.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping (value = "/createproject")
    @PreAuthorize("hasAuthority('LEADER')")
    public ResponseEntity<Project> createProject(@RequestBody CreateProjectDTO createProjectDTO) throws GroupException {

        Project p = projectService.createPrj(createProjectDTO);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }
}
