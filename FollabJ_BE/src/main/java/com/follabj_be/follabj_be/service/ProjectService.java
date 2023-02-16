package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.dto.CreateProjectDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.errorMessge.ErrorMessage;
import com.follabj_be.follabj_be.exception.GroupException;
import com.follabj_be.follabj_be.repository.ProjectRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import com.follabj_be.follabj_be.service.dependency.ProjectInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService implements ProjectInterface {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
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
        List<AppUser> member = new ArrayList<>();
        member.add(app_user);
        if(projectRepository.findByNameLike(p_name).isPresent()){
            throw new GroupException(ErrorMessage.PROJECT_DUPLICATE);
        }
        Project p = new Project(p_name, p_des, create_date, app_user, member);
        return projectRepository.save(p);
    }
}
