package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    @Query("select i from Invitation i where i.to.id=?1")
    List<Invitation> findByUserId(Long user_id);

    @Modifying
    @Transactional
    @Query("update from Invitation i set i.status = ?1 where i.id = ?2")
    void updateStatus(int status, Long in_id);
}
