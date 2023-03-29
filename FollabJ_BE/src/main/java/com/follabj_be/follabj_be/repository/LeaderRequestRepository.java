package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.entity.LeaderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LeaderRequestRepository extends JpaRepository<LeaderRequest, Long> {
    boolean existsByUserIdAndStatus(Long u_id, LeaderRequest.requestStatus status);

    @Query("select l from LeaderRequest l where l.status='PENDING'")
    Page<LeaderRequest> getPendingRequest(Pageable pageable);

}
