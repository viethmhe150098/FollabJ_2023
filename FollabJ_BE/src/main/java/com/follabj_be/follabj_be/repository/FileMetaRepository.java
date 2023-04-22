package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.entity.FileMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileMetaRepository extends JpaRepository<FileMeta, Long> {
    @Query("select f from FileMeta f where f.project.id=?1")
    Page<FileMeta> findFileMetaByProjectIdAndPage(Long p_id, Pageable pageable);

    List<FileMeta> findByProjectId(Long p_id);

    FileMeta findByIdAndProjectId(Long id, Long project_id);

}
