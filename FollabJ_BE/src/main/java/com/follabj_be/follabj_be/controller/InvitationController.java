package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.InvitationDTO;
import com.follabj_be.follabj_be.dto.ProjectDTO;
import com.follabj_be.follabj_be.dto.UpdateStatusDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Invitation;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.service.impl.InvitationService;
import com.follabj_be.follabj_be.service.impl.ProjectService;
import com.follabj_be.follabj_be.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/project/{p_id}/invitation")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public List<InvitationDTO> getInvitationsByProjectId(@PathVariable Long p_id) {
        List<Invitation> invitationList = invitationService.getInvitationsByProjectId(p_id);
        List<InvitationDTO> invitationDTOList = new ArrayList<>();

        for(Invitation invitation : invitationList) {
            InvitationDTO invitationDTO = modelMapper.map(invitation, InvitationDTO.class);
            invitationDTOList.add(invitationDTO);
        }

        return invitationDTOList;
    }

    @GetMapping("/invitation")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public List<InvitationDTO> getAllInvitationByUserId(Authentication authentication) {
        Long user_id = getUserId(authentication);
        List<Invitation> invitationList = invitationService.getInvitationsByUserId(user_id);
        List<InvitationDTO> invitationDTOList = new ArrayList<>();

        for (Invitation i: invitationList) {
            InvitationDTO invitationDTO = modelMapper.map(i, InvitationDTO.class);
            invitationDTOList.add(invitationDTO);
        }

        return invitationDTOList;
    }

    @PostMapping("/invitation/accept")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ProjectDTO acceptInvitationAndJoinProject(Authentication authentication, @RequestBody Invitation invitation) {
//        invitationService.updateStatus(1, invitation.getId());
        Long user_id = getUserId(authentication);
        checkIfInvitationBelongToUser(invitation.getId(), user_id);

        Project joinedProject =  projectService.addMember(invitation.getProject().getId(), user_id);
        invitationService.deleteInvitation(invitation.getId());

        ProjectDTO projectDTO = modelMapper.map(joinedProject, ProjectDTO.class);

        return projectDTO;
    }

    @DeleteMapping("/invitation/{invitation_id}")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public void rejectInvitation(Authentication authentication, @PathVariable Long invitation_id) {
        Long user_id = getUserId(authentication);
        checkIfInvitationBelongToUser(invitation_id, user_id);
        invitationService.deleteInvitation(invitation_id);
    }

    public Long getUserId(Authentication authentication) {
        return userService.getUserByEmail(authentication.getPrincipal().toString()).getId();
    }

    public void checkIfInvitationBelongToUser(Long invitation_id, Long user_id) {
        if(!invitationService.checkIfInvitationBelongToUser(invitation_id, user_id)) {
            throw new RuntimeException("Invitation not belong to user");
        }
    }
}
