package com.follabj_be.follabj_be.service;

import com.amazonaws.services.s3.model.S3Object;
import com.follabj_be.follabj_be.entity.FileMeta;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileMetaInterface {
    FileMeta upload(MultipartFile file, Long p_id, Long u_id) throws IOException;

    S3Object download(Long id, Long p_id);

    Page<FileMeta> list(Long p_id, int page);
}
