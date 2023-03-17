package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {



    @Query("Select p.members from Project p where p.id=?1")
    List<AppUser> getMembersById(Long project_id);

    @Query("Select p.leader from Project p where p.id=?1")
    AppUser getLeaderById(Long project_id);

    Optional<Project> findByNameLike(String name);

    @Query(nativeQuery = true, value = "Select * from project where id = ?1 and status = 1")
    Optional<Project> findById(Long id);

    @Query(nativeQuery = true, value = "Select project_id from project_members where user_id = ?1")
    List<Long> findByUserId(Long id);

    @Query(nativeQuery = true, value = "set status = 0 from project where id = ?1")
    void deleteProjectById(Long id);
}
