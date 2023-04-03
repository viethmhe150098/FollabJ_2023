package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.entity.LeaderRequest;
import com.follabj_be.follabj_be.service.impl.LeaderRequestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeaderRequestControllerTest {

    @Mock
    private LeaderRequestService leaderRequestService;

    @InjectMocks
    private LeaderRequestController leaderRequestController;

    @Test
    @DisplayName("should return an empty map when there are no pending requests")
    void getIsPendingReturnsEmptyMapWhenNoPendingRequests() {
        Page<LeaderRequest> pageRequest = new PageImpl<>(Collections.emptyList());
        when(leaderRequestService.getListRequest(1)).thenReturn(pageRequest);
        ResponseEntity<Map<Object, Object>> response = leaderRequestController.getIsPending(1, Optional.of(LeaderRequest.requestStatus.PENDING));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("status")).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("content")).isEqualTo(pageRequest);
        assertThat(response.getBody().get("curr_Page")).isEqualTo(pageRequest.getNumber());
        assertThat(response.getBody().get("total")).isEqualTo(pageRequest.getTotalPages());
    }

    @Test
    @DisplayName("should return a map with pending requests and related information")
    void getIsPendingReturnsPendingRequestsMap() {
        LeaderRequest leaderRequest = new LeaderRequest();
        Page<LeaderRequest> pageRequest = new PageImpl<>(Collections.singletonList(leaderRequest));
        when(leaderRequestService.getListRequest(1)).thenReturn(pageRequest);
        ResponseEntity<Map<Object, Object>> response = leaderRequestController.getIsPending(1, Optional.of(LeaderRequest.requestStatus.PENDING));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("status")).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("content")).isEqualTo(pageRequest);
        assertThat(response.getBody().get("curr_Page")).isEqualTo(pageRequest.getNumber());
        assertThat(response.getBody().get("total")).isEqualTo(pageRequest.getTotalPages());
    }

    @Test
    @DisplayName("should return a map with correct pagination information")
    void getIsPendingReturnsMapWithCorrectPagination() {
        int page = 1;
        LeaderRequest leaderRequest = new LeaderRequest();
        Page<LeaderRequest> pageRequest = new PageImpl<>(Collections.singletonList(leaderRequest));
        when(leaderRequestService.getListRequest(page)).thenReturn(pageRequest);

        ResponseEntity<Map<Object, Object>> response = leaderRequestController.getIsPending(page, Optional.of(LeaderRequest.requestStatus.PENDING));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("status")).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("content")).isEqualTo(pageRequest);
        assertThat(response.getBody().get("curr_Page")).isEqualTo(pageRequest.getNumber());
        assertThat(response.getBody().get("total")).isEqualTo(pageRequest.getTotalPages());
    }
}