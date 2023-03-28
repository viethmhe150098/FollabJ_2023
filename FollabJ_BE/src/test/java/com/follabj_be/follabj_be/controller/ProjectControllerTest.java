package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.CreateProjectDTO;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;
import com.follabj_be.follabj_be.exception.GroupException;
import com.follabj_be.follabj_be.service.impl.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @Test
    @DisplayName("should create a project and return it with HttpStatus.CREATED")
    void createProjectReturnsCreatedProject() throws GroupException {
        CreateProjectDTO createProjectDTO = new CreateProjectDTO();
        Project project = new Project();
        when(projectService.createPrj(createProjectDTO)).thenReturn(project);

        var result = projectController.createProject(createProjectDTO);

        assertEquals(project, result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    @DisplayName("should throw a GroupException when creating a project fails")
    void createProjectThrowsGroupExceptionOnFailure() throws GroupException {
        CreateProjectDTO createProjectDTO = new CreateProjectDTO();
        createProjectDTO.setId("1");
        createProjectDTO.setP_name("test");
        createProjectDTO.setP_des("test");
        when(projectService.createPrj(createProjectDTO))
                .thenThrow(new GroupException(CustomErrorMessage.PRJ01));

        GroupException exception =
                assertThrows(
                        GroupException.class,
                        () -> projectController.createProject(createProjectDTO));

        assertEquals("FL01:PROJECT'S NAME DUPLICATE", exception.getMessage());
    }
}