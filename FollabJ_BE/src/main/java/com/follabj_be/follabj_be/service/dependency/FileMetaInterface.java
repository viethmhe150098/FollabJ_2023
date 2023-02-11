package com.follabj_be.follabj_be.service.dependency;

import com.amazonaws.services.s3.model.S3Object;
import com.follabj_be.follabj_be.entity.FileMeta;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileMetaInterface {
    public void upload(MultipartFile file) throws IOException;
    public S3Object download(Long id);
    public List<FileMeta> list();

    void delete(Long id);
}
