package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.entity.MeetingType;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.exception.NoTypeException;
import com.follabj_be.follabj_be.repository.MeetingRepository;
import com.follabj_be.follabj_be.repository.MeetingTypeRepository;
import com.follabj_be.follabj_be.repository.ProjectRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeetingServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private MeetingTypeRepository meetingTypeRepository;

    @Mock
    private MeetingRepository meetingRepository;
    @InjectMocks
    private MeetingService meetingService;

    private Project project;
    private MeetingType meetingType;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setId(1L);
        project.setName("Project 1");

        meetingType = new MeetingType();
        meetingType.setId(1);
        // meetingType.setName("Meeting type 1");
    }

    @Test
    @DisplayName("Should return a hex string with leading zeros when necessary")
    void bytesToHexWithLeadingZeros() {
        byte[] input = new byte[]{0x01, 0x0A, 0x1F};
        int number = 3;

        String result =
                (String)
                        ReflectionTestUtils.invokeMethod(
                                meetingService, "bytesToHex", input, number);

        assertEquals("010a1f", result, "The hex string should have leading zeros when necessary");
    }

    @Test
    @DisplayName("Should return a 6-character hex string when given a byte array and number 3")
    void bytesToHexWithNumber3() {
        byte[] byteArray =
                new byte[]{(byte) 0x1A, (byte) 0x2B, (byte) 0x3C, (byte) 0x4D, (byte) 0x5E};
        int number = 3;

        String result =
                (String)
                        ReflectionTestUtils.invokeMethod(
                                meetingService, "bytesToHex", byteArray, number);

        assertEquals(6, result.length());
        assertEquals("1a2b3c", result);
    }

    @Test
    @DisplayName("Should return an 8-character hex string when given a byte array and number 4")
    void bytesToHexWithNumber4() {
        byte[] byteArray = new byte[]{(byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78};
        int number = 4;

        String result =
                (String)
                        ReflectionTestUtils.invokeMethod(
                                meetingService, "bytesToHex", byteArray, number);

        assertEquals("12345678", result, "The hex string should be 12345678");
    }

    @Test
    @DisplayName("Should throw NoTypeException when the type is not 1 or 2")
    void generateRoomIdWhenTypeIsNot1Or2ThenThrowNoTypeException() {
        Long projectId = 1L;
        int type = 3;

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // Act and Assert
        assertThrows(NoTypeException.class, () -> meetingService.generateRoomId(projectId, type));

        verify(projectRepository, times(1)).findById(projectId);
        verify(meetingTypeRepository, times(0)).findById(anyInt());
    }

    @Test
    @DisplayName("Should throw ObjectNotFoundException when the project is not found")
    void generateRoomIdWhenProjectNotFoundThenThrowObjectNotFoundException() {
        Long projectId = 1L;
        int type = 1;

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(
                ObjectNotFoundException.class,
                () -> meetingService.generateRoomId(projectId, type));

        verify(projectRepository, times(1)).findById(projectId);
        verify(meetingTypeRepository, times(0)).findById(anyInt());
    }

    @Test
    @DisplayName("Should generate a valid room ID for type 1 meeting")
    void generateRoomIdForType1Meeting() throws NoTypeException {
        Long projectId = 1L;
        int type = 1;
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(meetingTypeRepository.findById(type)).thenReturn(Optional.of(meetingType));

        String roomId = meetingService.generateRoomId(projectId, type);

        assertNotNull(roomId);
        verify(projectRepository, times(1)).findById(projectId);
        verify(meetingTypeRepository, times(1)).findById(type);
    }

    @Test
    @DisplayName("Should generate a valid room ID for type 2 meeting")
    void generateRoomIdForType2Meeting() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(meetingTypeRepository.findById(2)).thenReturn(Optional.of(meetingType));

        String roomId = null;
        try {
            roomId = meetingService.generateRoomId(1L, 2);
        } catch (NoTypeException e) {
            fail("NoTypeException should not be thrown");
        }

        assertNotNull(roomId);
        verify(projectRepository, times(1)).findById(1L);
        verify(meetingTypeRepository, times(1)).findById(2);
    }
}