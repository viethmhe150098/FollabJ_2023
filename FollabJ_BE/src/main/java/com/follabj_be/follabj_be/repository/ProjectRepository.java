package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.dto.ProjectCountDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // %dd/%MM/%YYYY

    @Query("Select p.members from Project p where p.id=?1")
    List<AppUser> getMembersById(Long project_id);

    @Query("Select p.leader from Project p where p.id=?1")
    AppUser getLeaderById(Long project_id);

    Optional<Project> findByName(String name);

    @Query(nativeQuery = true, value = "Select * from project where id = ?1 and status = 'ACTIVE'")
    Optional<Project> findById(Long id);

    @Query(nativeQuery = true, value = "Select project_id from project_members where user_id = ?1")
    List<Long> findByUserId(Long id);

    @Query("select p.id from Project p where p.leader.id=?1")
    List<Long> getProjectIdByLeaderID(Long u_id);

    @Query(nativeQuery = true, value = "select COUNT(id) from project where YEAR(str_to_date(created_date, '%d/%c/%Y')) = ?1")
    String countByYear(int year);

    @Query(nativeQuery = true, value = "select COUNT(id) from project where MONTH(str_to_date(created_date, '%d/%c/%Y')) = ?1")
    String countByMonth(int month);

    @Query(nativeQuery = true, value = "select COUNT(id) from project where DAY(str_to_date(created_date, '%d/%c/%Y')) = ?1")
    String countByDay(int day);

    @Query("Select p from Project p")
    Page<Project> getAllProject(Pageable pageable);

    @Query("Select p from Project p where p.name = ?1 and p.leader.id = ?2")
    Optional<Project> findByNameAndAndLeader(String name, Long u_id);

    @Query(nativeQuery = true, value="SELECT COUNT(ID) as count, \n" +
            "MONTH(str_to_date(created_date, '%d/%c/%Y')) as countBy\n" +
            "FROM project\n" +
            "where YEAR(str_to_date(created_date, '%d/%c/%Y')) = YEAR(CURDATE()) \n" +
            "group by MONTH(str_to_date(created_date, '%d/%c/%Y'))")
    List<ProjectCountDTO> projectPerMonth();

    @Query(nativeQuery = true, value = "SELECT COUNT(ID) as count, \n" +
            "DAY(str_to_date(created_date, '%d/%c/%Y')) as countBy\n" +
            "FROM project\n" +
            "where MONTH(str_to_date(created_date, '%d/%c/%Y')) = MONTH(CURDATE()) \n" +
            "group by DAY(str_to_date(created_date, '%d/%c/%Y'))")
    List<ProjectCountDTO> projectPerDay();

    @Query(nativeQuery = true, value = "SELECT COUNT(ID) as count, \n" +
            "YEAR(str_to_date(created_date, '%d/%c/%Y')) as countBy\n" +
            "FROM project\n" +
            "group by YEAR(str_to_date(created_date, '%d/%c/%Y'))")
    List<ProjectCountDTO> projectPerYear();
}
