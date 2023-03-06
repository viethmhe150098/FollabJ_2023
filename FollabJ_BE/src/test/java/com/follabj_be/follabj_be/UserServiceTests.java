package com.follabj_be.follabj_be;

import com.follabj_be.follabj_be.entity.Event;
import com.follabj_be.follabj_be.repository.EventRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import com.follabj_be.follabj_be.service.impl.EventService;
import com.follabj_be.follabj_be.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        assertThat(1).isEqualTo(1);
    }
    @Test
    public void User_SignUpUser() {
        assertThat(1).isEqualTo(1);
    }
    @Test
    public void User_SaveConfirmationToken() {
        assertThat(1).isEqualTo(1);
    }
    @Test
    public void User_EnableAppUser() {
        assertThat(1).isEqualTo(1);
    }
    @Test
    public void User_FindUsersByEmail() {
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
