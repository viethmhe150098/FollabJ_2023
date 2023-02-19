package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.entity.MeetingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingTypeRepository extends JpaRepository<MeetingType, Integer> {
}
