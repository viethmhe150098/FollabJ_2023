package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.dto.CreateProjectDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.*;
import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;
import com.follabj_be.follabj_be.exception.GroupException;
import com.follabj_be.follabj_be.repository.*;
import com.follabj_be.follabj_be.service.ProjectInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProjectService implements ProjectInterface {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;
    private final EventRepository eventRepository;
    private final TaskRepository taskRepository;

    private final MeetingRepository meetingRepository;
    @Override
    public Project createPrj(CreateProjectDTO createProjectDTO) throws GroupException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        Long user_id = Long.valueOf(createProjectDTO.getId());
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

    @Override
    public List<Project> getProjectByUserId(Long u_id) {
        List<Long> projects_id = projectRepository.findByUserId(u_id);
        List<Project> projects = new ArrayList<>();
        projects_id.stream().map(projectRepository::findById).toList().forEach(
                project -> projects.add(project.get())
        );
        return projects;
    }

    @Override
    @Transactional
    public void deleteProject(Long p_id) {
        Project p = projectRepository.findById(p_id).orElseThrow(() -> new ObjectNotFoundException("Not found project", p_id.toString()));
        for (AppUser u: p.getMembers()
             ) {
            u.getProjects().remove(p);
        }
        eventRepository.deleteAll(eventRepository.findByProjectId(p_id));
        taskRepository.deleteAll(taskRepository.findByProjectId(p_id));

        meetingRepository.deleteAll(meetingRepository.findByProject_id(p_id));
        projectRepository.deleteProjectById(p_id);
    }

    @Override
    @Transactional
    public void editProject(Long p_id, CreateProjectDTO createProjectDTO) {
        Project p = projectRepository.findById(p_id).orElseThrow(()-> new ObjectNotFoundException("Not found project", p_id.toString()));
        p.setName(createProjectDTO.getP_name());
        p.setDes(createProjectDTO.getP_des());
        projectRepository.save(p);
    }

    @Transactional
    @Override
    public void deleteMember(Long p_id, Long u_id) {
        Project p = projectRepository.findById(p_id).orElseThrow(()-> new ObjectNotFoundException("Not found project", p_id.toString()));
        p.setMembers(p.getMembers().stream().filter(m -> m.getId().equals(p_id)).collect(Collectors.toSet()));
        List<Event> events = eventRepository.findByProjectId(p_id);
        List<Task> tasks = taskRepository.findByProjectId(p_id);
        events.forEach(event -> event.setParticipantList(event.getParticipantList().stream().filter(e -> e.getId().equals(u_id)).collect(Collectors.toList())));

        tasks.forEach(task -> task.setAssigneeList(task.getAssigneeList().stream().filter(t -> t.getId().equals(u_id)).collect(Collectors.toList())));
    }


}
