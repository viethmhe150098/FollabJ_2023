package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.TaskDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.entity.Task;
import com.follabj_be.follabj_be.service.impl.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Task Controller Test")
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Mock
    private ModelMapper modelMapper;

    @Test
    @DisplayName("should return tasks by project id")
    void getTasksByProjectId() {
        Long projectId = 1L;
        Task task = new Task();
        TaskDTO taskDTO = new TaskDTO();
        when(taskService.getTasksByProjectId(projectId)).thenReturn(List.of(task));
        when(modelMapper.map(task, TaskDTO.class)).thenReturn(taskDTO);

        List<TaskDTO> taskDTOList = taskController.getTasksByProjectId(projectId);

        assertEquals(1, taskDTOList.size());
        assertEquals(taskDTO, taskDTOList.get(0));
    }

    @Test
    @DisplayName("should return all tasks")
    void getAllTasks() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("title");
        task.setDescription("description");
        task.setLabel("label");
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        task.setStatusId(1);
        task.setAssigneeList(new ArrayList<>());
        task.setProject(new Project());

        when(taskService.getAllTasks()).thenReturn(List.of(task));

        List<Task> tasks = taskController.getAllTasks();

        assertEquals(1, tasks.size());
        assertEquals(1L, tasks.get(0).getId());
        assertEquals("title", tasks.get(0).getTitle());
        assertEquals("description", tasks.get(0).getDescription());
        assertEquals("label", tasks.get(0).getLabel());
        assertEquals(1, tasks.get(0).getStatusId());
        assertNotNull(tasks.get(0).getAssigneeList());
        assertNotNull(tasks.get(0).getProject());
    }

    @Test
    @DisplayName("should add a task to a project")
    void addTask() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("title");
        task.setDescription("description");
        task.setLabel("label");
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        task.setStatusId(1);
        task.setAssigneeList(new ArrayList<>());
        task.setProject(new Project());
        task.getProject().setId(1L);

        when(taskService.addTask(task)).thenReturn(task);

        Task taskDTO = taskController.addTask(task, 1L);

        assertEquals(taskDTO.getId(), task.getId());
        assertEquals(taskDTO.getTitle(), task.getTitle());
        assertEquals(taskDTO.getDescription(), task.getDescription());
        assertEquals(taskDTO.getLabel(), task.getLabel());
        assertEquals(taskDTO.getStartDate(), task.getStartDate());
        assertEquals(taskDTO.getEndDate(), task.getEndDate());
        assertEquals(taskDTO.getStatusId(), task.getStatusId());
        assertEquals(taskDTO.getAssigneeList(), task.getAssigneeList());
    }

    @Test
    @DisplayName("should update a task")
    void updateTask() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("title");
        task.setDescription("description");
        task.setLabel("label");
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        task.setStatusId(1);
        task.setAssigneeList(new ArrayList<>());
        task.getAssigneeList().add(new AppUser());
        task.getAssigneeList().get(0).setId(1L);
        task.setProject(new Project());
        task.getProject().setId(1L);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setTitle("title");
        taskDTO.setDescription("description");
        taskDTO.setLabel("label");
        taskDTO.setStartDate(new Date());
        taskDTO.setEndDate(new Date());
        taskDTO.setStatusId(1);
        taskDTO.setAssigneeList(new ArrayList<>());
        taskDTO.getAssigneeList().add(new UserDTO());
        taskDTO.getAssigneeList().get(0).setId(1L);

        when(taskService.updateTask(1L, task)).thenReturn(task);
        when(modelMapper.map(task, TaskDTO.class)).thenReturn(taskDTO);

        TaskDTO result = taskController.updateTask(task, 1L, 1L);

        assertEquals(taskDTO, result);
    }

    @Test
    @DisplayName("should return a task by id")
    void getTaskById() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("title");
        task.setDescription("description");
        task.setLabel("label");
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        task.setStatusId(1);
        task.setAssigneeList(new ArrayList<>());
        task.getAssigneeList().add(new AppUser());
        task.getAssigneeList().get(0).setId(1L);
        task.getAssigneeList().get(0).setUsername("username");
        task.getAssigneeList().get(0).setEmail("email");
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setTitle("title");
        taskDTO.setDescription("description");
        taskDTO.setLabel("label");
        taskDTO.setStartDate(new Date());
        taskDTO.setEndDate(new Date());
        taskDTO.setStatusId(1);
        taskDTO.setAssigneeList(new ArrayList<>());
        taskDTO.getAssigneeList().add(new UserDTO());
        taskDTO.getAssigneeList().get(0).setId(1L);
        taskDTO.getAssigneeList().get(0).setUsername("username");
        taskDTO.getAssigneeList().get(0).setEmail("email");
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));
        when(modelMapper.map(task, TaskDTO.class)).thenReturn(taskDTO);

        TaskDTO result = taskController.getTaskById(1L);

        assertEquals(taskDTO, result);
    }
}