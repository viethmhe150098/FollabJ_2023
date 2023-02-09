package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @GetMapping("/project")
    @PreAuthorize("hasAuthority('LEADER')")
    public AppUser getProject(@RequestParam String id){
        return projectService.getLeaderId(id);
    }
}
