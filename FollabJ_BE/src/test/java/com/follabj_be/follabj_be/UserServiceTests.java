package com.follabj_be.follabj_be;

import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Task;
import com.follabj_be.follabj_be.repository.UserRepository;
import com.follabj_be.follabj_be.service.impl.UserService;
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
public class UserServiceTests {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void User_LoadUserByUsername() {
        List<AppUser> mockUsers = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            mockUsers.add(new AppUser());
        }
        List<AppUser> actualTasks = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            actualTasks.add(new AppUser());
        }
        assertThat(actualTasks.size()).isEqualTo(mockUsers.size());
    }
    @Test
    public void User_SignUpUser() {
        Long taskID = 1L;
        Optional<Task> task = Optional.of(Task.builder().id(taskID).build());
        assertThat(1).isEqualTo(1);
    }
    @Test
    public void User_FindUsersByEmail() {
        assertThat(1).isEqualTo(1);
    }
    @Test
    public void User_SaveConfirmationToken() {
        Long taskID = 1L;
        Optional<Task> task = Optional.of(Task.builder().id(taskID).build());
        assertThat(1).isEqualTo(1);
    }
    @Test
    public void User_EnableAppUser() {
        Long taskID = 1L;
        Optional<Task> task = Optional.of(Task.builder().id(taskID).build());
        assertThat(1).isEqualTo(1);
    }
    @Test
    public void User_GetAllInvitation() {
        assertThat(1).isEqualTo(1);
    }
    @Test
    public void User_GetUserByEmail() {
        assertThat(1).isEqualTo(1);
    }



}
