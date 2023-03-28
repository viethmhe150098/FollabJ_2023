package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.InvitationDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Invitation;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.service.impl.InvitationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvitationControllerTest {
    @Mock
    private InvitationService invitationService;

    @InjectMocks
    private InvitationController invitationController;

    @Test
    @DisplayName("should return an empty list when there are no invitations for the given user id")
    void getAllInvitationsByUserIdReturnsEmptyList() {
        when(invitationService.getInvitationsByUserId(anyLong())).thenReturn(new ArrayList<>());

        List<InvitationDTO> invitationDTOList = invitationController.getAllInvitationByUserId(1L);

        assertTrue(invitationDTOList.isEmpty());
    }

    @Test
    @DisplayName("should return all invitations for the given user id")
    void getAllInvitationsByUserId() {
        Long user_id = 1L;
        Invitation invitation = new Invitation();
        invitation.setId(1L);
        invitation.setReceiver(new AppUser());
        invitation.setProject(new Project());
        invitation.setStatus(0);
        List<Invitation> invitationList = new ArrayList<>();
        invitationList.add(invitation);

        when(invitationService.getInvitationsByUserId(user_id)).thenReturn(invitationList);

        List<InvitationDTO> invitationDTOList =
                invitationController.getAllInvitationByUserId(user_id);

        assertEquals(1, invitationDTOList.size());
    }
}