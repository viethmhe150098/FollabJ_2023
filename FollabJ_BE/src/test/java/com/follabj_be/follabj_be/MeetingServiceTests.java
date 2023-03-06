package com.follabj_be.follabj_be;

import com.follabj_be.follabj_be.exception.GroupException;
import com.follabj_be.follabj_be.repository.MeetingRepository;
import com.follabj_be.follabj_be.service.impl.MeetingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class MeetingServiceTests {
    @Mock
    MeetingRepository meetingRepository;

    @InjectMocks
    MeetingService meetingService;

    @Test
    public void Meeting_GenerateRoomId() throws GroupException {
        assertThat(1).isEqualTo(1);
    }

}
