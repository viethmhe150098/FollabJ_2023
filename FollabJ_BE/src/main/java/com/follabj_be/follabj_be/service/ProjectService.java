package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.dto.CreateProjectDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Invitation;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;
import com.follabj_be.follabj_be.exception.GroupException;
import com.follabj_be.follabj_be.repository.InvitationRepository;
import com.follabj_be.follabj_be.repository.ProjectRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import com.follabj_be.follabj_be.service.dependency.ProjectInterface;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ProjectService implements ProjectInterface {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, InvitationRepository invitationRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
    }


    @Override
    public Project createPrj(CreateProjectDTO createProjectDTO) throws GroupException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        Long user_id = Long.valueOf(createProjectDTO.getUser_id());
        AppUser app_user = userRepository.getAppUserById(user_id);
        String create_date = dtf.format(now);
        String p_name = createProjectDTO.getP_name();
        String p_des = createProjectDTO.getP_des();
        Set<AppUser> member = new HashSet<>();
        member.add(app_user);
        if(projectRepository.findByNameLike(p_name).isPresent()){
            throw new GroupException(CustomErrorMessage.PRJ01);
        }
        Project p = new Project(p_name, p_des, create_date, app_user, member);
        return projectRepository.save(p);
    }

    @Override
    public void sendInvitation(UserDTO user, Long project_id) {
        Project p = projectRepository.findById(project_id).orElseThrow(()-> new ObjectNotFoundException("Not found object", project_id.toString()));
        AppUser to = new AppUser(user.getId(), user.getUsername(), user.getEmail());
        String content = p.getName();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String create_date = dtf.format(now);
        Invitation i = new Invitation(to, create_date, content);
        invitationRepository.save(i);
    }

    public List<UserDTO> getMembersByProjectId(Long project_id) {
        List<AppUser> userList = projectRepository.getMembersById(project_id);
        List<UserDTO> userDTOList = new ArrayList<>();
        for(AppUser user: userList) {
            UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getUsername());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }
}
