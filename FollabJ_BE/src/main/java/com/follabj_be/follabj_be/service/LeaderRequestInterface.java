package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.dto.LeaderRequestDTO;
public interface LeaderRequestInterface {
    boolean isPendingRequest(Long u_id);
    void updateRequestStatus(Long req_id, int status);

    String saveRequest(LeaderRequestDTO leaderRequestDTO);
}
