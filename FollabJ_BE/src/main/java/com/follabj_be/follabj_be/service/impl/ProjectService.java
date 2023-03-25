package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.dto.CreateProjectDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.*;
import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;
import com.follabj_be.follabj_be.exception.GroupException;
import com.follabj_be.follabj_be.repository.*;
import com.follabj_be.follabj_be.service.EmailSender;
import com.follabj_be.follabj_be.service.ProjectInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

    private final FileMetaRepository fileMetaRepository;
    private final MeetingRepository meetingRepository;

    private final BuildEmail buildEmail;
    private final EmailSender emailSender;
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
        if (projectRepository.findByNameLike(p_name).isPresent()) {
            throw new GroupException(CustomErrorMessage.PRJ01);
        }
        Project p = new Project(p_name, p_des, create_date, app_user, member);
        return projectRepository.save(p);
    }

    @Override
    public void sendInvitation(UserDTO user, Long project_id) {
//        Project p = projectRepository.findById(project_id).orElseThrow(() -> new ObjectNotFoundException("Not found object", project_id.toString()));
//        AppUser to = new AppUser(user.getId(), user.getUsername(), user.getEmail());
//        String content = p.getName();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        LocalDateTime now = LocalDateTime.now();
//        String create_date = dtf.format(now);
//        Invitation i = new Invitation(to, create_date, content);
//        invitationRepository.save(i);
    }

    @Override
    public List<UserDTO> getMembersByProjectId(Long project_id) {

        List<AppUser> userList = projectRepository.getMembersById(project_id);
        List<UserDTO> userDTOList = new ArrayList<>();

        AppUser leader = projectRepository.getLeaderById(project_id);
        UserDTO leaderDTO = new UserDTO(leader.getId(), leader.getEmail(), leader.getUsername());

        userDTOList.add(leaderDTO);

        for (AppUser user : userList) {
            UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getUsername());
            if(!Objects.equals(userDTO.getId(), leaderDTO.getId())) {
                userDTOList.add(userDTO);
            }
        }
        return userDTOList;
    }

    @Override
    public List<Project> getProjectByUserId(Long u_id) {
        List<Long> projects_id = projectRepository.findByUserId(u_id);
        List<Project> projects = new ArrayList<>();
        projects_id.stream().map(projectRepository::findById).toList().forEach(
                project -> {
                    if(project.isPresent())
                    projects.add(project.get());
                }
        );
        return projects;
    }

    @Override
    @Transactional
    public void deleteProject(Long p_id) {
        Project p = projectRepository.findById(p_id).orElseThrow(() -> new ObjectNotFoundException("Not found project", p_id.toString()));

        System.out.println(p);
//        for (AppUser u : p.getMembers()
//        ) {
//            u.getProjects().remove(p);
//        }
//        eventRepository.deleteAll(eventRepository.findByProjectId(p_id));
//        taskRepository.deleteAll(taskRepository.findByProjectId(p_id));
//        meetingRepository.deleteAll(meetingRepository.findByProject_id(p_id));
//        fileMetaRepository.deleteAll(fileMetaRepository.findByProjectId(p_id));
//        projectRepository.deleteById(p_id);
    }

    @Override
    @Transactional
    public void editProject(Long p_id, CreateProjectDTO createProjectDTO) {
        Project p = projectRepository.findById(p_id).orElseThrow(() -> new ObjectNotFoundException("Not found project", p_id.toString()));
        p.setName(createProjectDTO.getP_name());
        p.setDes(createProjectDTO.getP_des());
        projectRepository.save(p);
    }

    @Transactional
    @Override
    public Project addMember(Long p_id, Long u_id) {
        Project p = projectRepository.findById(p_id).orElseThrow(() -> new ObjectNotFoundException("Not found project", p_id.toString()));

        AppUser newMember = new AppUser();
        newMember.setId(u_id);
        p.getMembers().add(newMember);
        p.setMembers(p.getMembers());

        return projectRepository.save(p);
    }

    @Transactional
    @Override
    public void deleteMember(Long p_id, Long u_id) {
        Project p = projectRepository.findById(p_id).orElseThrow(() -> new ObjectNotFoundException("Not found project", p_id.toString()));
        p.setMembers(p.getMembers().stream().filter(m -> m.getId().equals(p_id)).collect(Collectors.toSet()));
        List<Event> events = eventRepository.findByProjectId(p_id);
        List<Task> tasks = taskRepository.findByProjectId(p_id);
        events.forEach(event -> event.setParticipantList(event.getParticipantList().stream().filter(e -> e.getId().equals(u_id)).collect(Collectors.toList())));
        tasks.forEach(task -> task.setAssigneeList(task.getAssigneeList().stream().filter(t -> t.getId().equals(u_id)).collect(Collectors.toList())));
    }

    @Override
    @Transactional
    public void dactivateProject(Long p_id) {
        Project p = projectRepository.findById(p_id).orElseThrow(()-> new ObjectNotFoundException("Not found project", p_id.toString()));
        p.setStatus(Project.ProjectStatus.DEACTIVATE);
        projectRepository.save(p);
    }

    public Map<Object, Object> leaveGroup(Long p_id, Long u_id){
        String userEmail = userRepository.findById(u_id).orElseThrow(()-> new ObjectNotFoundException("Not found project", p_id.toString())).getEmail();
        Map<Object, Object> res = new HashMap<>();
        if(currentUser().getUsername().equals(userEmail)){
            deleteMember(p_id, u_id);
            res.put("status", 200);
            res.put("message", "Successful leaving");
        }else{
            res.put("status", 401);
            res.put("message", CustomErrorMessage.NO_PERMISSION.getMessage());
        }
        return res;
    }

    public Map<Object, Object> assignNewLeader(Long p_id, Long u_id){
        Map<Object, Object> res = new HashMap<>();
        Project p = projectRepository.findById(p_id).orElseThrow(()-> new ObjectNotFoundException("Not found project", p_id.toString()));
        if(checkMember(p_id, u_id)) {
            p.setLeader(userRepository.findById(u_id).orElseThrow(() -> new ObjectNotFoundException("Not found project", p_id.toString())));
            //userRepository.updateRole(u_id, 2);
            String userEmail = userRepository.findById(u_id).orElseThrow(() -> new ObjectNotFoundException("Not found project", p_id.toString())).getEmail();
            emailSender.sendEmail(userEmail, buildEmail.becomeLeader(userEmail));
            projectRepository.save(p);
            res.put("status", 200);
            res.put("message", "Successful new team leader appointment");
        }else{
            res.put("status", 500);
            res.put("message", CustomErrorMessage.NOT_TEAMMEMBER.getMessage());
        }
        return res;
    }

    public User currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    private boolean checkMember(Long project_id, Long user_id){
        List<AppUser> members_list = projectRepository.getMembersById(project_id);
        Optional<AppUser> member = members_list.stream().filter(user -> user.getId().equals(user_id)).findAny();
        if(member.isPresent()){
            return true;
        }else{
            return false;
        }
    }
}
