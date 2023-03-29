package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;
import com.follabj_be.follabj_be.exception.NoTypeException;
import com.follabj_be.follabj_be.service.impl.MeetingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeetingControllerTest {

    @Mock
    private MeetingService meetingService;

    @InjectMocks
    private MeetingController meetingController;

    @Test
    @DisplayName("should throw NoTypeException when invalid type is provided")
    void getRoomCodeWhenInvalidTypeProvidedThrowsNoTypeException() throws Exception {
        long projectId = 1L;
        int type = 3;
        when(meetingService.generateRoomId(projectId, type))
                .thenThrow(new NoTypeException(CustomErrorMessage.TYPE01));
        assertThrows(NoTypeException.class, () -> meetingController.getRoomCode(projectId, type));
    }

    @Test
    @DisplayName("should return room code when valid project id and type are provided")
    void getRoomCodeWhenValidProjectIdAndTypeProvided() throws Exception {
        long projectId = 1L;
        int type = 1;
        String roomCode = "roomCode";
        when(meetingService.generateRoomId(projectId, type)).thenReturn(roomCode);
        ResponseEntity<Map<Object, Object>> responseEntity =
                meetingController.getRoomCode(projectId, type);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(roomCode, responseEntity.getBody().get("roomCode"));
    }
}