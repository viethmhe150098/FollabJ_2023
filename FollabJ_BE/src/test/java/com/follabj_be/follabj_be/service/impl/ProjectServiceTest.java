package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.dto.CreateProjectDTO;
import com.follabj_be.follabj_be.dto.ProjectCountDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;
import com.follabj_be.follabj_be.exception.GroupException;
import com.follabj_be.follabj_be.repository.*;
import com.follabj_be.follabj_be.service.EmailSender;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProjectService")
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private InvitationRepository invitationRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private FileMetaRepository fileMetaRepository;

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private BuildEmail buildEmail;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private ProjectService projectService;

    @Test
    @DisplayName("Should return a list of projects count per year")
    void projectsPerYearReturnsListOfProjectCount() {
        List<ProjectCountDTO> expectedProjectsPerYear = new ArrayList<>();
        expectedProjectsPerYear.add(
                new ProjectCountDTO() {
                    @Override
                    public int getCount() {
                        return 5;
                    }

                    @Override
                    public String getCountBy() {
                        return "2021";
                    }
                });
        expectedProjectsPerYear.add(
                new ProjectCountDTO() {
                    @Override
                    public int getCount() {
                        return 3;
                    }

                    @Override
                    public String getCountBy() {
                        return "2020";
                    }
                });

        when(projectRepository.projectPerYear()).thenReturn(expectedProjectsPerYear);

        List<ProjectCountDTO> actualProjectsPerYear = projectService.projectsPerYear();

        assertEquals(expectedProjectsPerYear.size(), actualProjectsPerYear.size());
        for (int i = 0; i < expectedProjectsPerYear.size(); i++) {
            assertEquals(
                    expectedProjectsPerYear.get(i).getCount(),
                    actualProjectsPerYear.get(i).getCount());
            assertEquals(
                    expectedProjectsPerYear.get(i).getCountBy(),
                    actualProjectsPerYear.get(i).getCountBy());
        }

        verify(projectRepository, times(1)).projectPerYear();
    }

    @Test
    @DisplayName("Should return a list of projects count per day")
    void projectsPerDayReturnsListOfProjectCount() {
        List<ProjectCountDTO> expectedProjectsPerDay = new ArrayList<>();
        expectedProjectsPerDay.add(
                new ProjectCountDTO() {
                    @Override
                    public int getCount() {
                        return 5;
                    }

                    @Override
                    public String getCountBy() {
                        return "01/01/2022";
                    }
                });
        expectedProjectsPerDay.add(
                new ProjectCountDTO() {
                    @Override
                    public int getCount() {
                        return 3;
                    }

                    @Override
                    public String getCountBy() {
                        return "02/01/2022";
                    }
                });

        when(projectRepository.projectPerDay()).thenReturn(expectedProjectsPerDay);

        List<ProjectCountDTO> actualProjectsPerDay = projectService.projectsPerDay();

        assertEquals(expectedProjectsPerDay, actualProjectsPerDay);
        verify(projectRepository, times(1)).projectPerDay();
    }

    @Test
    @DisplayName("Should return a list of projects with their count per month")
    void projectsPerMonthReturnsListOfProjectCount() {
        List<ProjectCountDTO> expectedProjectsPerMonth = new ArrayList<>();
        expectedProjectsPerMonth.add(
                new ProjectCountDTO() {
                    @Override
                    public int getCount() {
                        return 5;
                    }

                    @Override
                    public String getCountBy() {
                        return "January";
                    }
                });
        expectedProjectsPerMonth.add(
                new ProjectCountDTO() {
                    @Override
                    public int getCount() {
                        return 3;
                    }

                    @Override
                    public String getCountBy() {
                        return "February";
                    }
                });

        when(projectRepository.projectPerMonth()).thenReturn(expectedProjectsPerMonth);

        List<ProjectCountDTO> actualProjectsPerMonth = projectService.projectsPerMonth();

        assertEquals(expectedProjectsPerMonth, actualProjectsPerMonth);
        verify(projectRepository, times(1)).projectPerMonth();
    }

    @Test
    @DisplayName("Should return true when the user is a member of the project")
    void checkMemberWhenUserIsMember() {
        Long projectId = 1L;
        Long userId = 2L;
        AppUser user = new AppUser();
        user.setId(userId);
        List<AppUser> membersList = Collections.singletonList(user);

        when(projectRepository.getMembersById(projectId)).thenReturn(membersList);

        boolean result =
                (boolean)
                        ReflectionTestUtils.invokeMethod(
                                projectService, "checkMember", projectId, userId);

        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when the user is not a member of the project")
    void checkMemberWhenUserIsNotMember() {
        Long projectId = 1L;
        Long userId = 2L;
        List<AppUser> membersList = new ArrayList<>();
        AppUser member1 = new AppUser();
        member1.setId(3L);
        membersList.add(member1);
        when(projectRepository.getMembersById(projectId)).thenReturn(membersList);

        boolean isMember =
                (boolean)
                        ReflectionTestUtils.invokeMethod(
                                projectService, "checkMember", projectId, userId);

        assertFalse(isMember);
    }

    @Test
    @DisplayName("Should return the current user's username")
    void currentUsernameReturnsCurrentUserUsername() {
        String expectedUsername = "test@example.com";
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(expectedUsername);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String actualUsername = projectService.currentUsername();

        assertEquals(
                expectedUsername,
                actualUsername,
                "The returned username should match the expected username");
    }

    @Test
    @DisplayName("Should assign a new leader when the user is a member of the project")
    void assignNewLeaderWhenUserIsMember() {
        Long projectId = 1L;
        Long userId = 2L;
        Project project = new Project();
        AppUser newLeader = new AppUser();
        newLeader.setId(userId);
        List<AppUser> members = new ArrayList<>();
        members.add(newLeader);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(userRepository.findById(userId)).thenReturn(Optional.of(newLeader));
        when(projectRepository.getMembersById(projectId)).thenReturn(members);

        Map<Object, Object> result = projectService.assignNewLeader(projectId, userId);

        assertEquals(200, result.get("status"));
        assertEquals("Successful new team leader appointment", result.get("message"));
        verify(projectRepository).save(project);
    }

    @Test
    @DisplayName("Should not assign a new leader when the user is not a member of the project")
    void assignNewLeaderWhenUserIsNotMember() {
        Long projectId = 1L;
        Long userId = 2L;
        Project project = new Project();
        project.setId(projectId);
        AppUser leader = new AppUser();
        leader.setId(3L);
        project.setLeader(leader);
        List<AppUser> members = new ArrayList<>();
        members.add(leader);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.getMembersById(projectId)).thenReturn(members);

        Map<Object, Object> result = projectService.assignNewLeader(projectId, userId);

        assertEquals(500, result.get("status"));
        assertEquals(CustomErrorMessage.NOT_TEAMMEMBER.getMessage(), result.get("message"));
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).getMembersById(projectId);
        verify(userRepository, never()).findById(userId);
        verify(projectRepository, never()).save(project);
    }

    @Test
    @DisplayName("Should return all projects")
    void getAllProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1L, "01/01/2022", Project.ProjectStatus.ACTIVE, new AppUser()));
        projects.add(new Project(2L, "02/01/2022", Project.ProjectStatus.ACTIVE, new AppUser()));
        projects.add(new Project(3L, "03/01/2022", Project.ProjectStatus.ACTIVE, new AppUser()));

        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> result = projectService.getAll();

        assertEquals(3, result.size());
        assertEquals(projects, result);
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return 'Wrong format' when the input format is incorrect")
    void countProjectsWhenInputFormatIsIncorrect() {
        String by = "INVALID_FORMAT";
        String expectedResult = "Wrong format";

        String actualResult = projectService.count(by);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Should return the count of projects by month")
    void countProjectsByMonth() {
        when(projectRepository.countByMonth(anyInt())).thenReturn("5");

        String result = projectService.count("MONTH");

        assertEquals("5", result);
        verify(projectRepository, times(1)).countByMonth(anyInt());
    }

    @Test
    @DisplayName("Should return the count of projects by year")
    void countProjectsByYear() {
        String by = "YEAR";
        String expectedCount = "5";
        when(projectRepository.countByYear(anyInt())).thenReturn(expectedCount);

        String actualCount = projectService.count(by);

        assertEquals(expectedCount, actualCount);
        verify(projectRepository).countByYear(anyInt());
    }

    @Test
    @DisplayName("Should return the count of projects by day")
    void countProjectsByDay() {
        String by = "DAY";
        String expectedCount = "5";
        when(projectRepository.countByDay(anyInt())).thenReturn(expectedCount);

        String actualCount = projectService.count(by);

        assertEquals(expectedCount, actualCount);
        verify(projectRepository).countByDay(anyInt());
    }

    @Test
    @DisplayName("Should throw an exception when an invalid project ID is provided")
    void dactivateProjectWithInvalidProjectIdThenThrowException() {
        Long invalidProjectId = 1L;
        when(projectRepository.findById(invalidProjectId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(
                ObjectNotFoundException.class,
                () -> projectService.dactivateProject(invalidProjectId));
        verify(projectRepository, times(1)).findById(invalidProjectId);
    }

    @Test
    @DisplayName("Should deactivate the project when a valid project ID is provided")
    void dactivateProjectWithValidProjectId() {
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);
        project.setStatus(Project.ProjectStatus.ACTIVE);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        projectService.dactivateProject(projectId);

        assertEquals(Project.ProjectStatus.DEACTIVATE, project.getStatus());
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    @DisplayName("Should not delete the member from the project when the member does not exist")
    void deleteMemberWhenMemberDoesNotExist() {
        Long projectId = 1L;
        Long userId = 2L;
        Project project = new Project();
        project.setId(projectId);
        project.setMembers(new HashSet<>());

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        projectService.deleteMember(projectId, userId);

        assertTrue(project.getMembers().isEmpty());
        verify(projectRepository, times(1)).findById(projectId);
        verify(eventRepository, times(1)).findByProjectId(projectId);
        verify(taskRepository, times(1)).findByProjectId(projectId);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    @DisplayName("Should delete the member from the project when the member exists")
    void deleteMemberWhenMemberExists() {
        Long projectId = 1L;
        Long userId = 2L;

        Project project = new Project();
        AppUser member1 = new AppUser();
        member1.setId(1L);
        AppUser member2 = new AppUser();
        member2.setId(userId);
        HashSet<AppUser> members = new HashSet<>();
        members.add(member1);
        members.add(member2);
        project.setMembers(members);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        projectService.deleteMember(projectId, userId);

        assertFalse(project.getMembers().contains(member2));
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    @DisplayName("Should throw an exception when the project ID is not found")
    void addMemberWhenProjectIdNotFoundThenThrowException() {
        Long projectId = 1L;
        Long userId = 2L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(
                ObjectNotFoundException.class, () -> projectService.addMember(projectId, userId));
        verify(projectRepository, times(1)).findById(projectId);
    }

    @Test
    @DisplayName(
            "Should throw an exception when trying to edit a project with an invalid project id")
    void editProjectWithInvalidProjectIdThenThrowException() {
        Long invalidProjectId = 1L;
        CreateProjectDTO createProjectDTO =
                new CreateProjectDTO("1", "New Project Name", "New Project Description");
        when(projectRepository.findById(invalidProjectId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(
                ObjectNotFoundException.class,
                () -> projectService.editProject(invalidProjectId, createProjectDTO));
        verify(projectRepository, times(1)).findById(invalidProjectId);
        verify(projectRepository, times(0)).save(any(Project.class));
    }

    @Test
    @DisplayName("Should edit the project with valid project id and updated project details")
    void editProjectWithValidProjectIdAndUpdatedDetails() {
        Long projectId = 1L;
        CreateProjectDTO createProjectDTO =
                new CreateProjectDTO("1", "Updated Project Name", "Updated Project Description");
        Project existingProject = new Project();
        existingProject.setId(projectId);
        existingProject.setName("Old Project Name");
        existingProject.setDes("Old Project Description");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));

        projectService.editProject(projectId, createProjectDTO);

        assertEquals(createProjectDTO.getP_name(), existingProject.getName());
        assertEquals(createProjectDTO.getP_des(), existingProject.getDes());
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).save(existingProject);
    }

    @Test
    @DisplayName("Should throw an exception when the project id is not found")
    void deleteProjectWhenProjectIdNotFoundThenThrowException() {
        Long projectId = 1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> projectService.deleteProject(projectId));
        verify(projectRepository, times(1)).findById(projectId);
    }

    @Test
    @DisplayName("Should delete the project when the project id is valid")
    void deleteProjectWhenProjectIdIsValid() {
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        projectService.deleteProject(projectId);
    }

    @Test
    @DisplayName(
            "Should return an empty list when no projects are associated with the given user ID")
    void getProjectByUserIdReturnsEmptyListWhenNoProjects() {
        Long userId = 1L;
        when(projectRepository.findByUserId(userId)).thenReturn(new ArrayList<>());

        List<Project> projects = projectService.getProjectByUserId(userId);

        assertTrue(projects.isEmpty());
        verify(projectRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Should return a list of projects for a given user ID")
    void getProjectByUserIdReturnsListOfProjects() {
        Long userId = 1L;
        List<Long> projectIds = new ArrayList<>();
        projectIds.add(1L);
        projectIds.add(2L);

        Project project1 = new Project();
        project1.setId(1L);
        project1.setName("Project 1");

        Project project2 = new Project();
        project2.setId(2L);
        project2.setName("Project 2");

        when(projectRepository.findByUserId(userId)).thenReturn(projectIds);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project1));
        when(projectRepository.findById(2L)).thenReturn(Optional.of(project2));

        List<Project> result = projectService.getProjectByUserId(userId);

        assertEquals(2, result.size());
        assertEquals("Project 1", result.get(0).getName());
        assertEquals("Project 2", result.get(1).getName());

        verify(projectRepository, times(1)).findByUserId(userId);
        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).findById(2L);
    }

    @Test
    @DisplayName("Should return a list of members including the leader for a given project ID")
    void getMembersByProjectIdReturnsListOfMembersIncludingLeader() {
        Long projectId = 1L;
        AppUser leader = new AppUser(1L, "leader@example.com", "leader");
        AppUser member1 = new AppUser(2L, "member1@example.com", "member1");
        AppUser member2 = new AppUser(3L, "member2@example.com", "member2");

        List<AppUser> members = new ArrayList<>();
        members.add(leader);
        members.add(member1);
        members.add(member2);

        when(projectRepository.getLeaderById(projectId)).thenReturn(leader);
        when(projectRepository.getMembersById(projectId)).thenReturn(members);

        List<UserDTO> result = projectService.getMembersByProjectId(projectId);

        assertEquals(3, result.size());
        assertEquals(leader.getId(), result.get(0).getId());
        assertEquals(member1.getId(), result.get(1).getId());
        assertEquals(member2.getId(), result.get(2).getId());

        verify(projectRepository, times(1)).getLeaderById(projectId);
        verify(projectRepository, times(1)).getMembersById(projectId);
    }

    @Test
    @DisplayName(
            "Should return 'already in project' when the user is already a member of the project")
    void sendInvitationWhenUserIsAlreadyInProject() {
        UserDTO userDTO = new UserDTO(1L, "test@example.com", "testuser");
        Long projectId = 1L;
        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setEmail("test@example.com");

        Project project = new Project();
        project.setId(1L);
        project.setMembers(new HashSet<>());
        project.getMembers().add(appUser);

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(userRepository.findByEmail(anyString())).thenReturn(appUser);

        String result = projectService.sendInvitation(userDTO, projectId);

        assertEquals("already in project", result);
    }

    @Test
    @DisplayName(
            "Should return 'already invited' when the user has already been invited to the project")
    void sendInvitationWhenUserIsAlreadyInvited() {
        UserDTO userDTO = new UserDTO(1L, "test@example.com", "testuser");
        Long projectId = 1L;
        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setEmail("test@example.com");
        Project project = new Project();
        project.setId(1L);
        project.setMembers(new HashSet<>());

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(userRepository.findByEmail(anyString())).thenReturn(appUser);
        when(invitationRepository.existsByReceiverIdAndProjectId(anyLong(), anyLong()))
                .thenReturn(true);

        String result = projectService.sendInvitation(userDTO, projectId);

        assertEquals("already invited", result);
        verify(projectRepository).findById(projectId);
        verify(userRepository).findByEmail(userDTO.getEmail());
        verify(invitationRepository)
                .existsByReceiverIdAndProjectId(appUser.getId(), project.getId());
    }

    @Test
    @DisplayName(
            "Should throw a GroupException when the project name is already taken by the same user")
    void createPrjWhenProjectNameIsAlreadyTakenBySameUserThenThrowGroupException() {
        CreateProjectDTO createProjectDTO =
                new CreateProjectDTO("1", "Test Project", "Test Description");
        AppUser appUser = new AppUser();
        appUser.setId(1L);
        Project existingProject =
                new Project("Test Project", "Test Description", "01/01/2022", appUser, null);
        existingProject.setLeader(appUser);

        when(userRepository.getAppUserById(anyLong())).thenReturn(appUser);
        when(projectRepository.findByName(anyString())).thenReturn(Optional.of(existingProject));

        // Act and Assert
        assertThrows(GroupException.class, () -> projectService.createPrj(createProjectDTO));
    }

    @Test
    @DisplayName("Should create a new project when the project name is not taken by the same user")
    void createPrjWhenProjectNameIsNotTakenBySameUser() {
        CreateProjectDTO createProjectDTO =
                new CreateProjectDTO("1", "Test Project", "Test Description");
        AppUser appUser = new AppUser();
        appUser.setId(1L);
        Project project =
                new Project(
                        "Test Project", "Test Description", "01/01/2022", appUser, new HashSet<>());
        when(userRepository.getAppUserById(anyLong())).thenReturn(appUser);
        when(projectRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project createdProject = null;
        try {
            createdProject = projectService.createPrj(createProjectDTO);
        } catch (GroupException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        assertNotNull(createdProject);
        assertEquals("Test Project", createdProject.getName());
        assertEquals("Test Description", createdProject.getDes());
        assertEquals("01/01/2022", createdProject.getCreatedDate());
        assertEquals(appUser, createdProject.getLeader());
    }
}