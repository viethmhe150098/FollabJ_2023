package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.TaskDTO;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.entity.Task;
import com.follabj_be.follabj_be.service.impl.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    final
    TaskService taskService;

    private final ModelMapper modelMapper;

    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/project/{project_id}/tasks")
    public List<TaskDTO> getTasksByProjectId(@PathVariable Long project_id) {
        List<Task> taskList = taskService.getTasksByProjectId(project_id);
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Task task: taskList) {
            TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
            taskDTOList.add(taskDTO);
        }

        return taskDTOList;
    }

    @GetMapping("/task")
    public List<Task>  getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/project/{project_id}/leader/tasks")
    @PreAuthorize("hasAuthority('LEADER')")
    public Task addTask(@RequestBody Task task,@PathVariable Long project_id) {
        task.setProject(new Project());
        task.getProject().setId(project_id);
        return taskService.addTask(task);
    }

    @GetMapping("/project/{project_id}/tasks/{task_id}")
    public TaskDTO getTaskById(@PathVariable Long task_id) {
        Optional<Task> optionalTask = taskService.getTaskById(task_id);
        return optionalTask.map(task -> modelMapper.map(task, TaskDTO.class)).orElse(null);

    }
    @RequestMapping(
            method=RequestMethod.PUT,
            path = "/project/{project_id}/leader/tasks/{task_id}/update"
    )
    @PreAuthorize("hasAuthority('LEADER')")
    public Task updateTask(@RequestBody Task task,@PathVariable Long project_id,@PathVariable Long task_id) {
        task.setProject(new Project());
        task.getProject().setId(project_id);
        return taskService.updateTask(task_id, task);
    }

    @RequestMapping(
            method=RequestMethod.DELETE,
            path = "/project/{project_id}/leader/tasks/{task_id}/delete"
    )
    @PreAuthorize("hasAuthority('LEADER')")
    public void deleteTask(@PathVariable Long task_id) {
        taskService.deleteTask(task_id);
    }

    @RequestMapping(
            method=RequestMethod.POST,
            path = "/project/{project_id}/leader/tasks/{task_id}/add"
    )
    @PreAuthorize("hasAuthority('LEADER')")
    public void addAssigneeToTask(@PathVariable Long task_id, @RequestParam Long assignee_id) {
        taskService.addAssigneeToTask(task_id, assignee_id);
    }

    @RequestMapping(
            method=RequestMethod.DELETE,
            path = "/project/{project_id}/leader/tasks/{task_id}/remove"
    )
    @PreAuthorize("hasAuthority('LEADER')")
    public void removeAssigneeFromTask(@PathVariable Long task_id, @RequestParam Long assignee_id) {
        taskService.removeAssigneeFromTask(task_id, assignee_id);
    }
}
