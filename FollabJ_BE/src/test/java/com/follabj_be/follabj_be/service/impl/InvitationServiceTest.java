package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Invitation;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.repository.InvitationRepository;
import com.follabj_be.follabj_be.repository.ProjectRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvitationServiceTest {
    @Mock
    private InvitationRepository invitationRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private InvitationService invitationService;

    @Test
    @DisplayName("Should update the invitation status when the invitation exists")
    void updateStatusWhenInvitationExists() {
        Long invitationId = 1L;
        int newStatus = 1;
        Invitation existingInvitation =
                new Invitation(invitationId, new AppUser(), new Project(), 0);

        when(invitationRepository.findById(invitationId))
                .thenReturn(Optional.of(existingInvitation));
        doNothing().when(invitationRepository).updateStatus(newStatus, invitationId);

        invitationService.updateStatus(newStatus, invitationId);

        verify(invitationRepository, times(1)).findById(invitationId);
        verify(invitationRepository, times(1)).updateStatus(newStatus, invitationId);
    }

    @Test
    @DisplayName("Should return an empty list when no invitations are found for a given user ID")
    void getInvitationsByUserIdReturnsEmptyListWhenNoInvitationsFound() {
        Long userId = 1L;
        when(invitationRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        List<Invitation> result = invitationService.getInvitationsByUserId(userId);

        assertTrue(result.isEmpty());
        verify(invitationRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Should return a list of invitations for a given user ID")
    void getInvitationsByUserIdReturnsListOfInvitations() {
        Long userId = 1L;
        AppUser user = new AppUser();
        user.setId(userId);

        Invitation invitation1 = new Invitation();
        invitation1.setId(1L);
        invitation1.setReceiver(user);

        Invitation invitation2 = new Invitation();
        invitation2.setId(2L);
        invitation2.setReceiver(user);

        List<Invitation> expectedInvitations = new ArrayList<>();
        expectedInvitations.add(invitation1);
        expectedInvitations.add(invitation2);

        when(invitationRepository.findByUserId(userId)).thenReturn(expectedInvitations);

        List<Invitation> actualInvitations = invitationService.getInvitationsByUserId(userId);

        assertThat(actualInvitations).isEqualTo(expectedInvitations);
        verify(invitationRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Should delete the invitation with the given id")
    void deleteInvitationWithGivenId() {
        Long invitationId = 1L;
        doNothing().when(invitationRepository).deleteById(invitationId);

        // Call the method under test
        invitationService.deleteInvitation(invitationId);

        verify(invitationRepository, times(1)).deleteById(invitationId);
    }

    @Test
    @DisplayName(
            "Should return null when the receiver ID and project ID do not match any invitation")
    void getInvitationByReceiverIdAndProjectIdWhenIdsDoNotMatch() {
        Long receiverId = 1L;
        Long projectId = 1L;
        when(invitationRepository.findByReceiverIdAndProjectId(receiverId, projectId))
                .thenReturn(null);

        Invitation result =
                invitationService.getInvitationByReceiverIdAndProjectId(receiverId, projectId);

        assertThat(result).isNull();
        verify(invitationRepository, times(1)).findByReceiverIdAndProjectId(receiverId, projectId);
    }

    @Test
    @DisplayName("Should return the invitation when the receiver ID and project ID match")
    void getInvitationByReceiverIdAndProjectIdWhenIdsMatch() {
        Long receiverId = 1L;
        Long projectId = 1L;
        AppUser receiver = new AppUser();
        Project project = new Project();
        Invitation expectedInvitation = new Invitation(1L, receiver, project, 0);

        when(invitationRepository.findByReceiverIdAndProjectId(receiverId, projectId))
                .thenReturn(expectedInvitation);

        Invitation actualInvitation =
                invitationService.getInvitationByReceiverIdAndProjectId(receiverId, projectId);

        assertThat(actualInvitation).isEqualTo(expectedInvitation);
        verify(invitationRepository, times(1)).findByReceiverIdAndProjectId(receiverId, projectId);
    }

    @Test
    @DisplayName("Should save the invitation successfully")
    void addInvitationSuccessfully() {
        Invitation invitation = new Invitation();
        invitation.setId(1L);
        invitation.setReceiver(new AppUser());
        invitation.setProject(new Project());
        invitation.setStatus(0);

        when(invitationRepository.save(invitation)).thenReturn(invitation);

        Invitation savedInvitation = invitationService.addInvitation(invitation);

        assertThat(savedInvitation).isNotNull();
        assertThat(savedInvitation.getId()).isEqualTo(invitation.getId());
        assertThat(savedInvitation.getReceiver()).isEqualTo(invitation.getReceiver());
        assertThat(savedInvitation.getProject()).isEqualTo(invitation.getProject());
        assertThat(savedInvitation.getStatus()).isEqualTo(invitation.getStatus());

        verify(invitationRepository, times(1)).save(invitation);
    }

    @Test
    @DisplayName("Should return an empty list when no invitations are found for a given project ID")
    void getInvitationsByProjectIdReturnsEmptyList() {
        Long projectId = 1L;
        when(invitationRepository.findByProjectId(projectId)).thenReturn(Collections.emptyList());

        List<Invitation> result = invitationService.getInvitationsByProjectId(projectId);

        assertTrue(result.isEmpty());
        verify(invitationRepository, times(1)).findByProjectId(projectId);
    }

    @Test
    @DisplayName("Should return a list of invitations for a given project ID")
    void getInvitationsByProjectId() {
        Long projectId = 1L;
        List<Invitation> expectedInvitations = new ArrayList<>();
        expectedInvitations.add(new Invitation(1L, null, null, 0));
        expectedInvitations.add(new Invitation(2L, null, null, 0));

        when(invitationRepository.findByProjectId(projectId)).thenReturn(expectedInvitations);

        List<Invitation> actualInvitations = invitationService.getInvitationsByProjectId(projectId);

        assertThat(actualInvitations).isEqualTo(expectedInvitations);
        verify(invitationRepository, times(1)).findByProjectId(projectId);
    }
}