package com.follabj_be.follabj_be.service.impl;

import com.amazonaws.services.s3.model.S3Object;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.FileMeta;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.repository.FileMetaRepository;
import com.follabj_be.follabj_be.repository.ProjectRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileMetaServiceTest {
    @Mock
    private AmazonS3Service amazonS3Service;

    @Mock
    private FileMetaRepository fileMetaRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FileMetaService fileMetaService;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Test
    @DisplayName(
            "Should return a page of FileMeta objects for the given project ID and page number")
    void listFileMetaByProjectIdAndPage() {
        Long projectId = 1L;
        int pageNumber = 0;
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<FileMeta> expectedFileMetaPage = mock(Page.class);

        when(fileMetaRepository.findFileMetaByProjectIdAndPage(projectId, pageable))
                .thenReturn(expectedFileMetaPage);

        Page<FileMeta> actualFileMetaPage = fileMetaService.list(projectId, pageNumber);

        assertThat(actualFileMetaPage).isEqualTo(expectedFileMetaPage);
        verify(fileMetaRepository).findFileMetaByProjectIdAndPage(projectId, pageable);
    }

    @Test
    @DisplayName("Should throw an exception when the file ID is not found")
    void downloadWhenFileIdNotFoundThenThrowException() {
        Long fileId = 1L;
        Long projectId = 1L;
        when(fileMetaRepository.findById(fileId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(
                EntityNotFoundException.class, () -> fileMetaService.download(fileId, projectId));
        verify(fileMetaRepository, times(1)).findById(fileId);
        verify(amazonS3Service, times(0)).download(any(), any());
    }

    @Test
    @DisplayName("Should download the file when the file ID and project ID are valid")
    void downloadWhenFileIdAndProjectIdAreValid() {
        Long fileId = 1L;
        Long projectId = 1L;
        FileMeta fileMeta = new FileMeta();
        fileMeta.setId(fileId);
        fileMeta.setFileName("test.txt");
        fileMeta.setFilePath("test-path");
        S3Object s3Object = new S3Object();

        when(fileMetaRepository.findById(fileId)).thenReturn(Optional.of(fileMeta));
        when(amazonS3Service.download(fileMeta.getFilePath(), fileMeta.getFileName()))
                .thenReturn(s3Object);

        S3Object result = fileMetaService.download(fileId, projectId);

        assertThat(result).isEqualTo(s3Object);
        verify(fileMetaRepository, times(1)).findById(fileId);
        verify(amazonS3Service, times(1)).download(fileMeta.getFilePath(), fileMeta.getFileName());
    }

    @Test
    @DisplayName("Should throw an exception when the file is empty")
    void uploadFileWhenFileIsEmptyThenThrowException() {
        MultipartFile file = mock(MultipartFile.class);
        Long p_id = 1L;
        Long u_id = 1L;

        when(file.isEmpty()).thenReturn(true);

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> fileMetaService.upload(file, p_id, u_id));
        verify(file, times(1)).isEmpty();
        verifyNoInteractions(
                amazonS3Service, fileMetaRepository, projectRepository, userRepository);
    }
}