package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.dto.LeaderRequestDTO;
import com.follabj_be.follabj_be.entity.LeaderRequest;
import org.springframework.data.domain.Page;

import java.util.List;


public interface LeaderRequestInterface {
    boolean isPendingRequest(Long u_id);
    void updateRequestStatus(Long req_id, int status);

    String saveRequest(LeaderRequestDTO leaderRequestDTO);

    Page<LeaderRequest> getListRequest(int page);

    LeaderRequest getRequestByUserId(Long u_id);

    Page<LeaderRequestDTO> getListRequest(int page, LeaderRequest.requestStatus status);

    List<LeaderRequestDTO> getListRequest(LeaderRequest.requestStatus status);


}
