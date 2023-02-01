package com.follabj_be.follabj_be.repository;
import com.follabj_be.follabj_be.entity.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskManagementRepository extends CrudRepository<Task, Long> {

}
