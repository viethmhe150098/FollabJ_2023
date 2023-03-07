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
    public void updateStatus(int status, Long i_id) {
        Invitation i = invitationRepository.findById(i_id).orElseThrow(() -> new ObjectNotFoundException("Not found invitation", i_id.toString()));
        invitationRepository.updateStatus(status, i_id);
        if (status == 1) {
            Project p = projectRepository.findByNameLike(i.getContent()).orElseThrow(() -> new ObjectNotFoundException("Not found object", i.getContent()));
            Set<AppUser> members = p.getMembers();
            members.add(i.getTo());
            p.setMembers(members);
            projectRepository.save(p);
        }
    }
}
