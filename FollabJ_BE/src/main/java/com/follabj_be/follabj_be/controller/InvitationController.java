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
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private ModelMapper modelMapper;

//    @GetMapping(value = "/project/{p_id}/invitations")
//    public List<UserDTO> getInvitationsByProjectId(@PathVariable Long p_id) {
//
//    }

    @PostMapping(value="/project/{p_id}/invite")



    @GetMapping("/invitation")
    public List<InvitationDTO> getAllInvitationByUserId(@RequestParam Long user_id) {
        List<Invitation> invitationList = invitationService.getInvitationsByUserId(user_id);
        List<InvitationDTO> invitationDTOList = new ArrayList<>();

        for (Invitation i: invitationList) {
            InvitationDTO invitationDTO = modelMapper.map(i, InvitationDTO.class);
            invitationDTOList.add(invitationDTO);
        }

        return invitationDTOList;
    }

    @GetMapping("/invitation/accept")
    public ProjectDTO acceptInvitationAndJoinProject(@RequestBody Invitation invitation) {
        invitationService.updateStatus(1, invitation.getId());
        Project joinedProject =  projectService.addMember(invitation.getProject().getId(), invitation.getReceiver().getId());

        ProjectDTO projectDTO = modelMapper.map(joinedProject, ProjectDTO.class);

        return projectDTO;
    }

}
