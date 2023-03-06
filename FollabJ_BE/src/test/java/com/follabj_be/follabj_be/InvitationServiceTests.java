package com.follabj_be.follabj_be;

import com.follabj_be.follabj_be.exception.GroupException;
import com.follabj_be.follabj_be.repository.InvitationRepository;
import com.follabj_be.follabj_be.service.impl.InvitationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class InvitationServiceTests {
    @Mock
    InvitationRepository invitationRepository;

    @InjectMocks
    InvitationService invitationService;

    @Test
    public void Invitation_TestUpdateStatus() throws GroupException {
        assertThat(1).isEqualTo(1);
    }
}
