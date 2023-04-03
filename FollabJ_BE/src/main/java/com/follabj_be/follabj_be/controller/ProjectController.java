package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.CreateProjectDTO;
import com.follabj_be.follabj_be.dto.InvitationDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Invitation;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.exception.GroupException;
import com.follabj_be.follabj_be.service.impl.InvitationService;
import com.follabj_be.follabj_be.service.impl.ProjectService;
import com.follabj_be.follabj_be.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    private final InvitationService invitationService;

    private UserService userService;

    private ModelMapper modelMapper;

    @PostMapping(value = "/createproject")
    @PreAuthorize("hasAuthority('LEADER')")
    public ResponseEntity<Project> createProject(@RequestBody CreateProjectDTO createProjectDTO) throws GroupException {

        Project p = projectService.createPrj(createProjectDTO);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @PostMapping(value = "/project/{p_id}/addmembers/leader")
    @PreAuthorize("hasAuthority('LEADER')")
    public ResponseEntity<Map<String, Object>> sendInvitation(@RequestBody UserDTO userDTO, @PathVariable("p_id") Long p_id) {
        String message = projectService.sendInvitation(userDTO, p_id);
        Map<String, Object> res = new HashMap<>();
        res.put("status", HttpStatus.OK.toString());
        res.put("message", message);

        Invitation invitation = invitationService.getInvitationByReceiverIdAndProjectId(userDTO.getId(), p_id);
        InvitationDTO invitationDTO = modelMapper.map(invitation, InvitationDTO.class);
        res.put("content", invitationDTO);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(value = "/project/{p_id}/members")
    public List<UserDTO> getProjectMembersByProjectId(@PathVariable Long p_id) {
        return projectService.getMembersByProjectId(p_id);
    }


    @GetMapping(value = "/{u_id}")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<Map<Object, Object>> getProjectByUserId(@PathVariable Long u_id) {
        List<Project> projects = projectService.getProjectByUserId(u_id);
        Map<Object, Object> res = new HashMap<>();
        res.put("status", HttpStatus.OK.toString());
        res.put("projects", projects);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping(value = "/project/{p_id}/leader")
    @PreAuthorize("hasAuthority('LEADER')")
    public ResponseEntity<Map<Object, Object>> deleteProject(@PathVariable Long p_id) {
        Map<Object, Object> res = new HashMap<>();
        res.put("status", HttpStatus.OK);
        projectService.deleteProject(p_id);
        res.put("message", "deleted project with id =" + p_id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping(value = "/project/{p_id}/leader")
    @PreAuthorize("hasAuthority('LEADER')")
    public ResponseEntity<Map<Object, Object>> editProject(@PathVariable Long p_id, @RequestBody CreateProjectDTO createProjectDTO) {
        projectService.editProject(p_id, createProjectDTO);
        Map<Object, Object> res = new HashMap<>();
        res.put("status", HttpStatus.OK);
        res.put("message", "edited project with id =" + p_id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping(value = "/project/{p_id}/leader/member/{u_id}")
    @PreAuthorize("hasAuthority('LEADER')")
    public ResponseEntity<Map<Object, Object>> deleteMember(@PathVariable Long p_id, @PathVariable Long u_id) {
        projectService.deleteMember(p_id, u_id);
        Map<Object, Object> res = new HashMap<>();
        res.put("status", HttpStatus.OK);
        res.put("message", "Deleted member with Id: " + u_id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(value = "/project/{p_id}/leader/deactivate")
    @PreAuthorize("hasAuthority('LEADER')")
    public ResponseEntity<Map<String, String>> deactivatePrj (@PathVariable Long p_id){
        projectService.dactivateProject(p_id);
        Map<String, String> res = new HashMap<>();
        res.put("status", HttpStatus.OK.toString());
        res.put("message", "Project "+p_id+" is deactivate and can not be active again");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(value = "/project/{p_id}/leave")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<Map<Object, Object>> leave(@PathVariable Long p_id, @RequestParam Long u_id){
        Map<Object, Object> res = projectService.leaveGroup(p_id, u_id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(value = "/project/{p_id}/assign")
    @PreAuthorize("hasAuthority('LEADER')")
    public ResponseEntity<Map<Object, Object>> assignNewLeader(@PathVariable Long p_id, @RequestParam Long u_id){
        projectService.assignNewLeader(p_id, u_id);
        return new ResponseEntity<>(projectService.assignNewLeader(p_id, u_id), HttpStatus.OK);
    }

}
