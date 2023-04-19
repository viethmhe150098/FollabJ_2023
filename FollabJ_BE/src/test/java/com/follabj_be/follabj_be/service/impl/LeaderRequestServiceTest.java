package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.dto.LeaderRequestDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.LeaderRequest;
import com.follabj_be.follabj_be.repository.LeaderRequestRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaderRequestServiceTest {

    @Mock
    private LeaderRequestRepository leaderRequestRepository;

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private LeaderRequestService leaderRequestService;

    @Test
    @DisplayName("Should return an empty list when there are no requests with the given status")
    void getListRequestWithNoRequestsForGivenStatus() {
        int page = 0;
        LeaderRequest.requestStatus status = LeaderRequest.requestStatus.PENDING;
        Pageable paging = PageRequest.of(page, 7);
        Page<LeaderRequest> emptyPage = new PageImpl<>(Collections.emptyList(), paging, 0);

        when(leaderRequestRepository.getRequestsByStatus(status, paging)).thenReturn(emptyPage);

        Page<LeaderRequest> result = leaderRequestService.getListRequest(page, status);

        assertEquals(emptyPage, result);
        verify(leaderRequestRepository, times(1)).getRequestsByStatus(status, paging);
    }

    @Test
    @DisplayName(
            "Should return a list of requests with the given status when the page number is greater than the total number of pages")
    void getListRequestWithPageNumberGreaterThanTotalPages() {
        int page = 2;
        LeaderRequest.requestStatus status = LeaderRequest.requestStatus.PENDING;
        Pageable pageable = PageRequest.of(page, 7);
        Page<LeaderRequest> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(leaderRequestRepository.getRequestsByStatus(status, pageable)).thenReturn(emptyPage);

        Page<LeaderRequest> result = leaderRequestService.getListRequest(page, status);

        assertEquals(emptyPage, result);
        verify(leaderRequestRepository, times(1)).getRequestsByStatus(status, pageable);
    }

    @Test
    @DisplayName("Should return a list of requests with the given status and pagination")
    void getListRequestWithStatusAndPagination() {
        int page = 0;
        LeaderRequest.requestStatus status = LeaderRequest.requestStatus.PENDING;
        Pageable pageable = PageRequest.of(page, 7);
        LeaderRequest leaderRequest = new LeaderRequest();
        leaderRequest.setStatus(status);
        Page<LeaderRequest> leaderRequestPage =
                new PageImpl<>(Collections.singletonList(leaderRequest));

        when(leaderRequestRepository.getRequestsByStatus(status, pageable))
                .thenReturn(leaderRequestPage);

        Page<LeaderRequest> result = leaderRequestService.getListRequest(page, status);

        assertEquals(leaderRequestPage, result);
        assertEquals(1, result.getContent().size());
        assertEquals(status, result.getContent().get(0).getStatus());
        verify(leaderRequestRepository, times(1)).getRequestsByStatus(status, pageable);
    }

    @Test
    @DisplayName("Should return null when no leader request is found for the given user ID")
    void getRequestByUserIdReturnsNullWhenNotFound() {
        Long userId = 1L;
        when(leaderRequestRepository.findByUserId(userId)).thenReturn(null);

        LeaderRequest result = leaderRequestService.getRequestByUserId(userId);

        assertNull(result);
        verify(leaderRequestRepository).findByUserId(userId);
    }

    @Test
    @DisplayName("Should return the leader request for the given user ID")
    void getRequestByUserIdReturnsLeaderRequest() {
        Long userId = 1L;
        LeaderRequest expectedLeaderRequest = new LeaderRequest();
        expectedLeaderRequest.setId(1L);
        expectedLeaderRequest.setUser(new AppUser(userId, "testUsername", "testEmail"));
        when(leaderRequestRepository.findByUserId(userId)).thenReturn(expectedLeaderRequest);

        LeaderRequest actualLeaderRequest = leaderRequestService.getRequestByUserId(userId);

        assertEquals(expectedLeaderRequest, actualLeaderRequest);
        verify(leaderRequestRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Should return an empty page when there are no pending requests")
    void getListRequestWhenNoPendingRequests() {
        int page = 0;
        Pageable paging = PageRequest.of(page, 7);
        when(leaderRequestRepository.getPendingRequest(paging)).thenReturn(Page.empty());

        Page<LeaderRequest> result = leaderRequestService.getListRequest(page);

        assertTrue(result.isEmpty());
        verify(leaderRequestRepository, times(1)).getPendingRequest(paging);
    }


    @Test
    @DisplayName("Should return a page of pending requests with the given page number")
    void getListRequestWithGivenPageNumber() {
        int pageNumber = 0;
        Pageable pageable = PageRequest.of(pageNumber, 7);
        Page<LeaderRequest> expectedPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(leaderRequestRepository.getPendingRequest(pageable)).thenReturn(expectedPage);

        Page<LeaderRequest> actualPage = leaderRequestService.getListRequest(pageNumber);

        assertEquals(expectedPage, actualPage);
        verify(leaderRequestRepository, times(1)).getPendingRequest(pageable);
    }

    @Test
    @DisplayName("Should return a message when there is a pending request for the user")
    void saveRequestWhenPendingRequestExists() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO(userId, "test@example.com", "testuser");
        LeaderRequestDTO leaderRequestDTO =
                new LeaderRequestDTO(
                        null, "Test message", userDTO, LeaderRequest.requestStatus.PENDING);

        when(leaderRequestRepository.existsByUserIdAndStatus(
                userId, LeaderRequest.requestStatus.PENDING))
                .thenReturn(true);

        String result = leaderRequestService.saveRequest(leaderRequestDTO);

        assertEquals("You have 1 request waiting to be approved, please wait", result);
        verify(leaderRequestRepository, times(1))
                .existsByUserIdAndStatus(userId, LeaderRequest.requestStatus.PENDING);
    }

    @Test
    @DisplayName(
            "Should save the leader request and return a success message when there is no pending request for the user")
    void saveRequestWhenNoPendingRequest() {
        LeaderRequestDTO leaderRequestDTO = new LeaderRequestDTO();
        UserDTO userDTO = new UserDTO(1L, "test@example.com", "testuser");
        leaderRequestDTO.setUser(userDTO);
        leaderRequestDTO.setMessage("Test message");

        AppUser appUser = new AppUser(1L, "testuser", "test@example.com");

        when(leaderRequestRepository.existsByUserIdAndStatus(
                userDTO.getId(), LeaderRequest.requestStatus.PENDING))
                .thenReturn(false);
        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.of(appUser));
        when(leaderRequestRepository.save(any(LeaderRequest.class)))
                .thenReturn(new LeaderRequest());

        String result = leaderRequestService.saveRequest(leaderRequestDTO);

        assertEquals("The request has been sent to the administrator", result);
        verify(leaderRequestRepository)
                .existsByUserIdAndStatus(userDTO.getId(), LeaderRequest.requestStatus.PENDING);
        verify(userRepository).findById(userDTO.getId());
        verify(leaderRequestRepository).save(any(LeaderRequest.class));
    }

    @Test
    @DisplayName("Should throw an ObjectNotFoundException when the request is not found")
    void updateRequestStatusWhenRequestNotFoundThenThrowException() {
        Long req_id = 1L;
        int status = 1;
        when(leaderRequestRepository.findById(req_id)).thenReturn(Optional.empty());

        assertThrows(
                ObjectNotFoundException.class,
                () -> {
                    leaderRequestService.updateRequestStatus(req_id, status);
                });

        // Verify
        verify(leaderRequestRepository, times(1)).findById(req_id);
    }

    @Test
    @DisplayName("Should update the request status to REJECT when status is not 1")
    void updateRequestStatusWhenStatusIsNot1() {
        Long reqId = 1L;
        int status = 0;
        LeaderRequest leaderRequest = new LeaderRequest();
        leaderRequest.setId(reqId);
        leaderRequest.setStatus(LeaderRequest.requestStatus.PENDING);

        when(leaderRequestRepository.findById(reqId)).thenReturn(Optional.of(leaderRequest));

        leaderRequestService.updateRequestStatus(reqId, status);

        assertEquals(LeaderRequest.requestStatus.REJECT, leaderRequest.getStatus());
        verify(leaderRequestRepository, times(1)).save(leaderRequest);
    }

    @Test
    @DisplayName("Should return true when there is a pending request for the given user ID")
    void isPendingRequestWhenPendingRequestExists() {
        Long userId = 1L;
        when(leaderRequestRepository.existsByUserIdAndStatus(
                userId, LeaderRequest.requestStatus.PENDING))
                .thenReturn(true);

        boolean result = leaderRequestService.isPendingRequest(userId);

        assertTrue(result);
        verify(leaderRequestRepository)
                .existsByUserIdAndStatus(userId, LeaderRequest.requestStatus.PENDING);
    }

    @Test
    @DisplayName("Should return false when there is no pending request for the given user ID")
    void isPendingRequestWhenNoPendingRequestExists() {
        Long userId = 1L;
        when(leaderRequestRepository.existsByUserIdAndStatus(
                userId, LeaderRequest.requestStatus.PENDING))
                .thenReturn(false);

        boolean result = leaderRequestService.isPendingRequest(userId);

        assertFalse(
                result,
                "Expected isPendingRequest to return false when there is no pending request for the given user ID");
        verify(leaderRequestRepository, times(1))
                .existsByUserIdAndStatus(userId, LeaderRequest.requestStatus.PENDING);
    }
}