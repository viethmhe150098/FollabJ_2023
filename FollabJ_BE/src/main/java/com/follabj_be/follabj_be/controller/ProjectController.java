package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.CreateProjectDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.exception.GroupException;
import com.follabj_be.follabj_be.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping(value = "/project/{p_id}/addmembers")
    @PreAuthorize("hasAuthority('LEADER')")
    public ResponseEntity<Map<String, String>> sendInvitation(@RequestBody UserDTO userDTO, @PathVariable("p_id") Long p_id) {
        projectService.sendInvitation(userDTO, p_id);
        Map<String, String> res = new HashMap<>();
        res.put("status", HttpStatus.OK.toString());
        res.put("message", "Send Invitation success");

        return new ResponseEntity<>(res, HttpStatus.OK) ;
    }

    @GetMapping(value = "/{u_id}")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<Map<Object, Object>> getProjectByUserId(@PathVariable Long u_id){
        List<Project> projects = projectService.getProjectByUserId(u_id);
        Map<Object, Object> res = new HashMap<>();
        res.put("status", HttpStatus.OK.toString());
        res.put("projects", projects);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
