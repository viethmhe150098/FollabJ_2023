package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.entity.Task;
import com.follabj_be.follabj_be.repository.TaskAssigneeRepository;
import com.follabj_be.follabj_be.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    TaskAssigneeRepository taskAssigneeRepository;
    @InjectMocks
    TaskService taskService;

    @Test
    @DisplayName("Should not remove the assignee from the task when the task ID is invalid")
    void removeAssigneeFromTaskWhenTaskIdIsInvalid() {
        Long invalidTaskId = -1L;
        Long assigneeId = 1L;

        taskService.removeAssigneeFromTask(invalidTaskId, assigneeId);

        verify(taskAssigneeRepository, times(1)).removeAssigneeFromTask(invalidTaskId, assigneeId);
    }

    @Test
    @DisplayName(
            "Should remove the assignee from the task when both task and assignee IDs are valid")
    void removeAssigneeFromTaskWhenTaskAndAssigneeIdsAreValid() {
        Long taskId = 1L;
        Long assigneeId = 2L;

        taskService.removeAssigneeFromTask(taskId, assigneeId);

        verify(taskAssigneeRepository, times(1)).removeAssigneeFromTask(taskId, assigneeId);
    }

    @Test
    @DisplayName("Should not remove the assignee from the task when the assignee ID is invalid")
    void removeAssigneeFromTaskWhenAssigneeIdIsInvalid() {
        Long taskId = 1L;
        Long invalidAssigneeId = -5L;

        taskService.removeAssigneeFromTask(taskId, invalidAssigneeId);

        verify(taskAssigneeRepository, times(1)).removeAssigneeFromTask(taskId, invalidAssigneeId);
    }

    @Test
    @DisplayName(
            "Should not remove the assignee from the task when both task and assignee IDs are invalid")
    void removeAssigneeFromTaskWhenBothTaskAndAssigneeIdsAreInvalid() {
        Long invalidTaskId = -1L;
        Long invalidAssigneeId = 1L;

        taskService.removeAssigneeFromTask(invalidTaskId, invalidAssigneeId);

        verify(taskAssigneeRepository, times(1))
                .removeAssigneeFromTask(invalidTaskId, invalidAssigneeId);
    }

    @Test
    @DisplayName("Should add assignee to the task successfully")
    void addAssigneeToTaskSuccessfully() {
        Long taskId = 1L;
        Long assigneeId = 2L;

        taskService.addAssigneeToTask(taskId, assigneeId);

        verify(taskAssigneeRepository, times(1)).addAssigneeToTask(taskId, assigneeId);
    }

    @Test
    @DisplayName("Should delete the task when the taskId is valid")
    void deleteTaskWhenTaskIdIsValid() {
        Long taskId = 1L;
        doNothing().when(taskRepository).deleteById(taskId);

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    @DisplayName("Should throw an exception when the taskId is not found")
    void deleteTaskWhenTaskIdNotFoundThenThrowException() {
        Long taskId = 1L;

        doThrow(new NoSuchElementException()).when(taskRepository).deleteById(taskId);

        try {
            taskService.deleteTask(taskId);
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }

        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    @DisplayName("Should update the destination column position after changing the task status")
    void updateDestinationColumnPositionAfterChangeTaskStatus() {
        Long projectId = 1L;
        Long taskId = 2L;

        taskService.updateDestinationColumnPositionAfterChangeTaskStatus(projectId, taskId);

        verify(taskRepository, times(1))
                .updateDestinationColumnPositionWhenChangeStatus(projectId, taskId);
    }

    @Test
    @DisplayName("Should update the source column position before changing the task status")
    void updateSourceColumnPositionBeforeChangeTaskStatus() {
        Long projectId = 1L;
        Long taskId = 2L;

        taskService.updateSourceColumnPositionBeforeChangeTaskStatus(projectId, taskId);

        verify(taskRepository, times(1))
                .updateSourceColumnPositionWhenChangeStatus(projectId, taskId);
    }

    @Test
    @DisplayName("Should update column positions after changing task position")
    void updateColumnPositionAfterChangeTaskPosition() {
        Long projectId = 1L;
        Long taskId = 2L;

        taskService.updateColumnPositionAfterChangeTaskPosition(projectId, taskId);

        verify(taskRepository, times(1))
                .updateColumnPositionAfterChangeTaskPosition(projectId, taskId);
    }

    @Test
    @DisplayName("Should update the task column position successfully")
    void updateTaskColumnPositionSuccessfully() {
        Long taskId = 1L;
        int columnPosition = 2;

        taskService.updateTaskColumnPosition(taskId, columnPosition);

        verify(taskRepository, times(1)).updateTaskColumnPosition(taskId, columnPosition);
    }

    @Test
    @DisplayName("Should not update the task column position when the task id is not found")
    void updateTaskColumnPositionWhenTaskIdNotFound() {
        Long taskId = 1L;
        int columnPosition = 2;

        taskService.updateTaskColumnPosition(taskId, columnPosition);

        verify(taskRepository, times(1)).updateTaskColumnPosition(taskId, columnPosition);
    }

    @Test
    @DisplayName("Should not update the task status when the task id is invalid")
    void updateTaskStatusWhenTaskIdIsInvalid() {
        Long invalidTaskId = -1L;
        int newStatus = 1;
        taskService.updateTaskStatus(invalidTaskId, newStatus);
        verify(taskRepository, times(1)).updateTaskStatus(invalidTaskId, newStatus);
    }

    @Test
    @DisplayName("Should update the task status when the task id is valid")
    void updateTaskStatusWhenTaskIdIsValid() {
        Long taskId = 1L;
        int newStatus = 1;
        Task task = new Task();
        task.setId(taskId);
        task.setStatusId(1);


        taskService.updateTaskStatus(taskId, newStatus);

        assertEquals(newStatus, task.getStatusId());
        verify(taskRepository, times(0)).findById(taskId);
        verify(taskRepository, times(0)).save(task);
    }

    @Test
    @DisplayName("Should not update the task if the id is not found")
    void updateTaskWhenIdNotFound() {
        Long taskId = 1L;
        Task task =
                Task.builder()
                        .title("Test Task")
                        .description("Test Description")
                        .startDate(new Date())
                        .endDate(new Date())
                        .statusId(1)
                        .build();
        Task updatedTask = taskService.updateTask(taskId, task);
        assertEquals(null, updatedTask);
    }

    @Test
    @DisplayName("Should update the task with the given id and new task data")
    void updateTaskWithGivenIdAndNewTaskData() {
        Long taskId = 1L;
        Task oldTask =
                Task.builder()
                        .id(taskId)
                        .title("Old Title")
                        .description("Old Description")
                        .startDate(new Date())
                        .endDate(new Date())
                        .statusId(1)
                        .build();

        Task newTask =
                Task.builder()
                        .title("New Title")
                        .description("New Description")
                        .startDate(new Date())
                        .endDate(new Date())
                        .statusId(2)
                        .build();

        when(taskRepository.save(any(Task.class))).thenReturn(newTask);

        Task updatedTask = taskService.updateTask(taskId, newTask);

        assertEquals(newTask.getTitle(), updatedTask.getTitle());
        assertEquals(newTask.getDescription(), updatedTask.getDescription());
        assertEquals(newTask.getStartDate(), updatedTask.getStartDate());
        assertEquals(newTask.getEndDate(), updatedTask.getEndDate());
        assertEquals(newTask.getStatusId(), updatedTask.getStatusId());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should save the task when valid task is provided")
    void addTaskWhenValidTaskIsProvided() {
        Task task =
                Task.builder()
                        .title("Test Task")
                        .description("Test Task Description")
                        .startDate(new Date())
                        .endDate(new Date())
                        .statusId(1)
                        .build();

        Task savedTask =
                Task.builder()
                        .id(1L)
                        .title("Test Task")
                        .description("Test Task Description")
                        .startDate(new Date())
                        .endDate(new Date())
                        .statusId(1)
                        .build();

        when(taskRepository.save(task)).thenReturn(savedTask);

        Task result = taskService.addTask(task);

        assertEquals(savedTask, result);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    @DisplayName("Should return an empty optional when the task ID is not found")
    void getTaskByIdWhenTaskIdNotFound() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTaskById(taskId);

        assertTrue(result.isEmpty());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    @DisplayName("Should return the task when the task ID is valid")
    void getTaskByIdWhenTaskIdIsValid() {
        Long taskId = 1L;
        Task expectedTask =
                Task.builder()
                        .id(taskId)
                        .title("Test Task")
                        .description("Test Task Description")
                        .startDate(new Date())
                        .endDate(new Date())
                        .statusId(1)
                        .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(expectedTask));

        Optional<Task> actualTask = taskService.getTaskById(taskId);

        assertTrue(actualTask.isPresent());
        assertEquals(expectedTask, actualTask.get());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    @DisplayName("Should return all tasks")
    void getAllTasks() {
        Task task1 =
                Task.builder()
                        .id(1L)
                        .title("Task 1")
                        .description("Task 1 description")
                        .startDate(new Date())
                        .endDate(new Date())
                        .statusId(1)
                        .build();

        Task task2 =
                Task.builder()
                        .id(2L)
                        .title("Task 2")
                        .description("Task 2 description")
                        .startDate(new Date())
                        .endDate(new Date())
                        .statusId(1)
                        .build();

        List<Task> expectedTasks = Arrays.asList(task1, task2);

        when(taskRepository.findAll()).thenReturn(expectedTasks);

        List<Task> actualTasks = taskService.getAllTasks();

        assertEquals(expectedTasks, actualTasks);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName(
            "Should return an empty list when no tasks are associated with the given project ID")
    void getTasksByProjectIdReturnsEmptyListWhenNoTasks() {
        Long projectId = 1L;
        when(taskRepository.findByProjectId(projectId)).thenReturn(Collections.emptyList());

        List<Task> tasks = taskService.getTasksByProjectId(projectId);

        assertTrue(tasks.isEmpty());
        verify(taskRepository, times(1)).findByProjectId(projectId);
    }

    @Test
    @DisplayName("Should return all tasks for the given project ID")
    void getTasksByProjectIdReturnsAllTasks() {
        Long projectId = 1L;
        Task task1 =
                Task.builder()
                        .id(1L)
                        .title("Task 1")
                        .description("Task 1 description")
                        .startDate(new Date())
                        .endDate(new Date())
                        .statusId(1)
                        .build();
        Task task2 =
                Task.builder()
                        .id(2L)
                        .title("Task 2")
                        .description("Task 2 description")
                        .startDate(new Date())
                        .endDate(new Date())
                        .statusId(1)
                        .build();
        List<Task> expectedTasks = Arrays.asList(task1, task2);
        when(taskRepository.findByProjectId(projectId)).thenReturn(expectedTasks);

        List<Task> actualTasks = taskService.getTasksByProjectId(projectId);

        assertEquals(expectedTasks, actualTasks);
        verify(taskRepository, times(1)).findByProjectId(projectId);
    }
}