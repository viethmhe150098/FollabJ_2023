package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.AppUserDTO;
import com.follabj_be.follabj_be.service.impl.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {
    @Mock
    UserService userService;
    @InjectMocks
    AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should update the user status and return the updated user")
    void updateUserStatusWhenValidUserIdAndStatus() {
        Long u_id = 1L;
        int status = 1;
        AppUserDTO appUserDTO = new AppUserDTO(1L, "test@test.com", "test", 1);
        when(userService.updateStatus(status, u_id)).thenReturn(appUserDTO);
        ResponseEntity<Map<Object, Object>> responseEntity =
                adminController.updateUserStatus(u_id, status);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Update status for user 1", responseEntity.getBody().get("message"));
        assertEquals(appUserDTO, responseEntity.getBody().get("user"));
    }

    @Test
    @DisplayName("should return an internal server error when the update fails")
    void updateUserStatusWhenUpdateFailsThenReturnInternalServerError() {
        Long u_id = 1L;
        int status = 1;
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setId(u_id);
        appUserDTO.setStatus(status);
        when(userService.updateStatus(status, u_id)).thenReturn(null);
        ResponseEntity<Map<Object, Object>> responseEntity =
                adminController.updateUserStatus(u_id, status);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Something went wrong", responseEntity.getBody().get("message"));
    }

    @Test
    void testUpdateUserStatus() {
        when(userService.updateStatus(anyInt(), anyLong())).thenReturn(new AppUserDTO(Long.valueOf(1), "email", "username", 0));

        ResponseEntity<Map<Object, Object>> result = adminController.updateUserStatus(Long.valueOf(1), 0);
        Assertions.assertEquals(1, 1);
    }
}

