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
    @Query("select l from LeaderRequest l where l.user.id = ?1 and l.status= ?2")
    boolean getByStatus(Long u_id, int status);

    @Query("select l from LeaderRequest l where l.status=0")
    Page<LeaderRequest> getPendingRequest(Pageable pageable);

}
