package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.TaskDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.entity.Task;
import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;
import com.follabj_be.follabj_be.exception.GroupException;
import com.follabj_be.follabj_be.service.impl.TaskService;
import com.follabj_be.follabj_be.service.impl.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin
public class TaskController {

    @Autowired
    final TaskService taskService;

    @Autowired
    UserService userService;

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
    public ResponseEntity<Object> addTask(@Valid @RequestBody Task task, @PathVariable Long project_id) {
        checkIfTaskAssigneeListValid(task, project_id);
        task.setProject(new Project());
        task.getProject().setId(project_id);
        Task addedTask = taskService.addTask(task);
        return new ResponseEntity<>(modelMapper.map(addedTask, TaskDTO.class), HttpStatus.OK);
    }

    @GetMapping("/project/{project_id}/tasks/{task_id}")
    public TaskDTO getTaskById(@PathVariable Long task_id) {
        Optional<Task> optionalTask = taskService.getTaskById(task_id);
        return optionalTask.map(task -> modelMapper.map(task, TaskDTO.class)).orElse(null);
    }

    @PutMapping("/project/{project_id}/leader/tasks/{task_id}/update")
    public TaskDTO updateTask(@Valid @RequestBody Task task, @PathVariable Long project_id, @PathVariable Long task_id) {
        checkIfTaskBelongToProject(task_id, project_id);
        checkIfTaskAssigneeListValid(task, project_id);
        task.setProject(new Project());
        task.getProject().setId(project_id);
        Task updatedTask = taskService.updateTask(task_id, task);
        TaskDTO taskDTO = modelMapper.map(updatedTask, TaskDTO.class);
        return taskDTO;
    }

    @PutMapping("/project/{project_id}/tasks/{task_id}/changeColumn")
    public ResponseEntity<Map<String, Object>> changeColumn(@RequestParam int status, @RequestParam int columnPosition, @PathVariable Long project_id, @PathVariable Long task_id) {
        checkIfTaskBelongToProject(task_id, project_id);
        taskService.updateSourceColumnPositionBeforeChangeTaskStatus( project_id, task_id);
        taskService.updateTaskColumnPosition(task_id, columnPosition);
        taskService.updateTaskStatus(task_id, status);
        taskService.updateDestinationColumnPositionAfterChangeTaskStatus(project_id, task_id);

        Map<String, Object> res = new HashMap<>();
        res.put("message", "Change task column successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    

    @PutMapping("/project/{project_id}/tasks/{task_id}/changePosition")
    public ResponseEntity<Map<String, Object>> changePosition (@RequestParam int columnPosition, @PathVariable Long project_id, @PathVariable Long task_id) {
        checkIfTaskBelongToProject(task_id, project_id);
        Task task = taskService.getTaskById(task_id).get();

        if(task.getColumnPosition() > columnPosition) {
            taskService.updateColumnPositionWhenChangeTaskPositionUp(project_id, task_id, columnPosition);
        } else {
            taskService.updateColumnPositionWhenChangeTaskPositionDown(project_id, task_id, columnPosition);
        }
        taskService.updateTaskColumnPosition(task_id, columnPosition);

        //taskService.updateColumnPositionAfterChangeTaskPosition(project_id, task_id);

        Map<String, Object> res = new HashMap<>();
        res.put("message", "Change task position successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @DeleteMapping("/project/{project_id}/leader/tasks/{task_id}/delete")
    public void deleteTask(@PathVariable Long project_id,@PathVariable Long task_id) {
        checkIfTaskBelongToProject(task_id, project_id);
        taskService.updateSourceColumnPositionBeforeChangeTaskStatus(project_id, task_id);
        taskService.deleteTask(task_id);
    }

    public void checkIfTaskBelongToProject(Long task_id, Long project_id) {
        if(!taskService.checkIfTaskExistInProject(project_id, task_id)) {
            throw new RuntimeException("Task id not existed in project");
        }
    }

    public void checkIfTaskAssigneeListValid(Task task, Long project_id) {
        task.getAssigneeList().forEach(member -> {
            if(userService.checkIfUserExistInProject(project_id, member.getId()) == 0)
                throw new RuntimeException("[assigneeList] " +CustomErrorMessage.NOT_TEAMMEMBER.getMessage());
        });
    }
}
