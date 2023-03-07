package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.entity.LeaderRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaderRequestRepository extends JpaRepository<LeaderRequest, Long> {
    @Query("select l from LeaderRequest l where l.user.id = ?1 and l.status=0")
    boolean requestIsPending(Long u_id);
}
