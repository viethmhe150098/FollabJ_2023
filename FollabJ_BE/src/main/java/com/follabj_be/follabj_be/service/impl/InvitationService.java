package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Invitation;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.repository.InvitationRepository;
import com.follabj_be.follabj_be.repository.ProjectRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import com.follabj_be.follabj_be.service.InvitationInterface;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class InvitationService implements InvitationInterface {
    private final InvitationRepository invitationRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public InvitationService(InvitationRepository invitationRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.invitationRepository = invitationRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Invitation> getInvitationsByProjectId(Long project_id) {
        return invitationRepository.findByProjectId(project_id);
    }

    @Override
    public Invitation addInvitation(Invitation invitation) {
        return invitationRepository.save(invitation);
    }

    @Override
    public void deleteInvitation(Long invitation_id) {
        invitationRepository.deleteById(invitation_id);
    }

    @Override
    public List<Invitation> getInvitationsByUserId(Long user_id) {
        return invitationRepository.findByUserId(user_id);
    }

    @Override
    public void updateStatus(int status, Long i_id) {
        Invitation i = invitationRepository.findById(i_id).orElseThrow(() -> new ObjectNotFoundException("Not found invitation", i_id.toString()));
        invitationRepository.updateStatus(status, i_id);

    }
}
