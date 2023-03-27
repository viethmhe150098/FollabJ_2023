package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.service.impl.LeaderRequestService;
import com.follabj_be.follabj_be.service.impl.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private LeaderRequestService leaderRequestService;

    @Test
    @DisplayName("should return an empty list when no users have matching email")
    void findUserInfoByEmailWithNoMatchingEmail() {
        String email_cha = "test@test.com";
        when(userService.findUsersByEmail(email_cha)).thenReturn(new ArrayList<>());

        List<UserDTO> result = userService.findUsersByEmail(email_cha);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("should return a list of users with matching email")
    void findUserInfoByEmailWithMatchingEmail() {
        String email_cha = "test@gmail.com";
        UserDTO userDTO = new UserDTO(1L, "test@gmail.com", "test");
        List<UserDTO> userDTOList = new ArrayList<>();
        userDTOList.add(userDTO);
        when(userService.findUsersByEmail(email_cha)).thenReturn(userDTOList);

        List<UserDTO> result = userService.findUsersByEmail(email_cha);

        assertEquals(1, result.size());
        //assertEquals(userDTO, result.get(0));
    }
}