package com.follabj_be.follabj_be;
import com.follabj_be.follabj_be.entity.Task;
import com.follabj_be.follabj_be.repository.TaskAssigneeRepository;
import com.follabj_be.follabj_be.repository.TaskRepository;
import com.follabj_be.follabj_be.service.impl.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {
    @Mock
    TaskRepository taskRepository;
    @Mock
    TaskAssigneeRepository taskAssigneeRepository;
    @InjectMocks
    TaskService taskService;

    @Test
    void Task_GetAllTaskTest(){
        List<Task> mockTasks = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            mockTasks.add(new Task());
        }
        // 2. define behavior of Repository
        when(taskRepository.findAll()).thenReturn(mockTasks);
        // 3. call service method
        List<Task> actualTasks = taskService.getAllTasks();
        // 4. assert the result
        assertThat(actualTasks.size()).isEqualTo(mockTasks.size());
        // 4.1 ensure repository is called
        verify(taskRepository).findAll();
    }


    @Test
    public void Task_TestUpdateTask() {
        Task task = new Task();
        task.setId(1L);
        when(taskRepository.save(task)).thenReturn(task);
        Task updateTask = taskService.updateTask(1L, task);
        assertThat(task).isEqualTo(updateTask);
        verify(taskRepository).save(task);
    }

    @Test
    public void Task_TestDeleteTask() {
        Long taskID = 1L;
        taskService.deleteTask(taskID);
        verify(taskRepository).deleteById(taskID);
    }

    @Test
    public void Task_TestGetTaskById() {
        Long taskID = 1L;
        Optional<Task> task = Optional.of(Task.builder().id(taskID).build());
        Mockito.when(taskRepository.findById(taskID)).thenReturn(task);
        Optional<Task> foundTask = taskService.getTaskById(taskID);
        assertThat(task).isEqualTo(foundTask);
        verify(taskRepository).findById(taskID);
    }

    @Test
    public void Task_TestCreateUser() {
        Task task = new Task();
        when(taskRepository.save(task)).thenReturn(task);
        Task savedTask = taskService.addTask(task);
        assertThat(task).isEqualTo(savedTask);
        verify(taskRepository).save(task);
    }
}
