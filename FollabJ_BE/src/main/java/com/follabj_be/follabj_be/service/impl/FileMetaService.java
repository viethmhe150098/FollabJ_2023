package com.follabj_be.follabj_be.service.impl;

import com.amazonaws.services.s3.model.S3Object;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.FileMeta;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.repository.FileMetaRepository;
import com.follabj_be.follabj_be.repository.ProjectRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import com.follabj_be.follabj_be.service.FileMetaInterface;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service

public class FileMetaService implements FileMetaInterface {
    private final AmazonS3Service amazonS3Service;

    private final FileMetaRepository fileMetaRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public FileMetaService(AmazonS3Service amazonS3Service, FileMetaRepository fileMetaRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.amazonS3Service = amazonS3Service;
        this.fileMetaRepository = fileMetaRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public FileMeta upload(MultipartFile file, Long p_id, Long u_id) throws IOException {
        if (file.isEmpty()) throw new IllegalStateException("Cannot upload empty file");

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s/%s", bucketName, p_id, UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());

        // Uploading file to s3
        amazonS3Service.upload(
                path, fileName, Optional.of(metadata), file.getInputStream());
        Date local = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String uploadDate = dateFormat.format(local);
        Project p = projectRepository.findById(p_id).orElseThrow(() -> new ObjectNotFoundException("Project not found", p_id.toString()));
        AppUser ap = userRepository.getAppUserById(u_id);
        // Saving metadata to db
        return fileMetaRepository.save(new FileMeta(fileName, path, uploadDate, p, ap));
    }

    @Override
    public S3Object download(Long id, Long p_id) {
        FileMeta fileMeta = fileMetaRepository.findByIdAndProjectId(id, p_id);
        if(fileMeta == null) {
            throw new EntityNotFoundException("File Not Found");
        }
        return amazonS3Service.download(fileMeta.getFilePath(), fileMeta.getFileName());
    }

    @Override
    public Page<FileMeta> list(Long p_id, int page) {
        Pageable paging = PageRequest.of(page, 6);
        return fileMetaRepository.findFileMetaByProjectIdAndPage(p_id, paging);
    }

    @Override
    public List<FileMeta> listAllByProject(Long p_id) {
        return fileMetaRepository.findByProjectId(p_id);
    }


}
