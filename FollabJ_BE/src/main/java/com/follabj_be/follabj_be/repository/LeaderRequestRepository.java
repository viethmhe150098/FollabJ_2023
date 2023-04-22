package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.dto.LeaderRequestDTO;
import com.follabj_be.follabj_be.entity.LeaderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LeaderRequestRepository extends JpaRepository<LeaderRequest, Long> {
    boolean existsByUserIdAndStatus(Long u_id, LeaderRequest.requestStatus status);

    @Query("select l from LeaderRequest l where l.status='PENDING'")
    Page<LeaderRequest> getPendingRequest(Pageable pageable);

    LeaderRequest findByUserId(Long u_id);

    @Query("select l from LeaderRequest l where l.status=?1")
    Page<LeaderRequest> getRequestsByStatus(LeaderRequest.requestStatus status, Pageable pageable);

    @Query("select l from LeaderRequest l where l.status=?1")
    List<LeaderRequest> getLeaderRequestByStatus(LeaderRequest.requestStatus status);
}
