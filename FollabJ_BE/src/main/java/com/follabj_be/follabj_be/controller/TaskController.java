package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.TaskDTO;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.entity.Task;
import com.follabj_be.follabj_be.service.impl.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class TaskController {

    @Autowired
    final TaskService taskService;

    private final ModelMapper modelMapper;

    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/project/{project_id}/tasks")
    public List<TaskDTO> getTasksByProjectId(@PathVariable Long project_id) {
        List<Task> taskList = taskService.getTasksByProjectId(project_id);
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Task task : taskList) {
            TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
            taskDTOList.add(taskDTO);
        }

        return taskDTOList;
    }

    @GetMapping("/task")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/project/{project_id}/leader/tasks")
    public Task addTask(@RequestBody Task task, @PathVariable Long project_id) {
        task.setProject(new Project());
        task.getProject().setId(project_id);
        return taskService.addTask(task);
    }

    @GetMapping("/project/{project_id}/tasks/{task_id}")
    public TaskDTO getTaskById(@PathVariable Long task_id) {
        Optional<Task> optionalTask = taskService.getTaskById(task_id);
        return optionalTask.map(task -> modelMapper.map(task, TaskDTO.class)).orElse(null);

    }

    @PutMapping("/project/{project_id}/leader/tasks/{task_id}/update")
    public TaskDTO updateTask(@RequestBody Task task, @PathVariable Long project_id, @PathVariable Long task_id) {
        task.setProject(new Project());
        task.getProject().setId(project_id);
        Task updatedTask = taskService.updateTask(task_id, task);
        TaskDTO taskDTO = modelMapper.map(updatedTask, TaskDTO.class);
        return taskDTO;
    }

    @PutMapping("/project/{project_id}/leader/tasks/{task_id}/changeColumn")
    public void changeColumn(@RequestParam int status, @RequestParam int columnPosition, @PathVariable Long project_id, @PathVariable Long task_id) {
        taskService.updateSourceColumnPositionBeforeChangeTaskStatus( project_id, task_id);
        taskService.updateTaskColumnPosition(task_id, columnPosition);
        taskService.updateTaskStatus(task_id, status);
        taskService.updateDestinationColumnPositionAfterChangeTaskStatus(project_id, task_id);
    }

    @PutMapping("/project/{project_id}/leader/tasks/{task_id}/changePosition")
    public void changePosition (@RequestParam int columnPosition, @PathVariable Long project_id, @PathVariable Long task_id) {
        taskService.updateTaskColumnPosition(task_id, columnPosition);
        taskService.updateColumnPositionAfterChangeTaskPosition(project_id, task_id);
    }

    @DeleteMapping("/project/{project_id}/leader/tasks/{task_id}/delete")
    public void deleteTask(@PathVariable Long task_id) {
        taskService.deleteTask(task_id);
    }
}
