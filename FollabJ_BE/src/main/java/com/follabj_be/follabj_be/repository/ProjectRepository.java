package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    Optional<Project> findById(Long id);
    @Query(nativeQuery = true, value="Select project_id from project_members where user_id = ?1")
    List<Long> findByUserId(Long id);

    void deleteProjectById(Long id);

    @Query("select p.id from Project p where p.leader.id=?1")
    List<Long> getProjectIdByLeaderID(Long u_id);

    @Query(nativeQuery = true, value = "select COUNT(id) from project where YEAR(str_to_date(created_date, '%Y/%m/%d %T')) = ?1")
    String countByYear(int year);

    @Query(nativeQuery = true, value = "select COUNT(id) from project where MONTH(str_to_date(created_date, '%Y/%m/%d %T')) = ?1")
    String countByMonth(int month);

    @Query(nativeQuery = true, value = "select COUNT(id) from project where DAY(str_to_date(created_date, '%Y/%m/%d %T')) = ?1")
    String countByDay(int day);
}
