package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a SET a.status= ?1 WHERE a.email=?2")
    int enableAppUser(int status, String email);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update user_roles set role_id=1 where id=?1")
    int updateRole(Long id);

    AppUser getAppUserById(Long id);

    @Query("select new com.follabj_be.follabj_be.dto.UserDTO(u.id, u.username, u.email)from AppUser u where upper(u.email) like %:email_cha%")
    List<UserDTO> findByEmailLike(String email_cha);

    AppUser findByEmail(String email);
}