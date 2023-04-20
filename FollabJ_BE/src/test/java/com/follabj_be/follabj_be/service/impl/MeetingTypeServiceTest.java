package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.entity.MeetingType;
import com.follabj_be.follabj_be.repository.MeetingTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeetingTypeServiceTest {
    @Mock
    private MeetingTypeRepository meetingTypeRepository;

    @InjectMocks
    private MeetingTypeService meetingTypeService;

    @Test
    @DisplayName("Should initialize meeting types when the repository is empty")
    void initWhenRepositoryIsEmpty() {
        when(meetingTypeRepository.findAll()).thenReturn(Collections.emptyList());

        meetingTypeService.init();

        verify(meetingTypeRepository, times(0)).save(new MeetingType(1, "INSTANT"));
        verify(meetingTypeRepository, times(0)).save(new MeetingType(2, "LATER"));
    }

    @Test
    @DisplayName("Should not initialize meeting types when the repository is not empty")
    void initWhenRepositoryIsNotEmpty() {
        List<MeetingType> meetingTypes =
                List.of(new MeetingType(1, "INSTANT"), new MeetingType(2, "LATER"));
        when(meetingTypeRepository.findAll()).thenReturn(meetingTypes);

        meetingTypeService.init();

        verify(meetingTypeRepository, times(0)).save(any(MeetingType.class));
    }
}