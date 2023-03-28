package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.entity.FileMeta;
import com.follabj_be.follabj_be.service.impl.FileMetaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileControllerTest {
    @Mock
    private FileMetaService fileMetaService;

    @InjectMocks
    private FileController fileController;

    private Long projectId = 1L;
    private Long userId = 1L;
    private FileMeta fileMeta = new FileMeta();

    @Test
    @DisplayName("should return correct page number when multiple pages are present")
    void getAllFilesWithCorrectPageNumber() {
        List<FileMeta> fileMetaList = Collections.singletonList(fileMeta);
        org.springframework.data.domain.Page<FileMeta> fileMetaPage = new PageImpl<>(fileMetaList);
        when(fileMetaService.list(anyLong(), anyInt())).thenReturn(fileMetaPage);

        ResponseEntity<Map<Object, Object>> response = fileController.getAllFiles(projectId, 1);
        Map<Object, Object> responseBody = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, responseBody.get("page"));
        assertEquals(0, responseBody.get("current_page"));
        assertEquals(1, responseBody.get("total"));
        assertEquals(fileMetaList, responseBody.get("data"));
    }

    @Test
    @DisplayName("should return empty list when no files are present")
    void getAllFilesWhenNoFilesPresent() {
        when(fileMetaService.list(anyLong(), anyInt()))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        Map<Object, Object> response = (Map<Object, Object>) fileController.getAllFiles(projectId, 1);
        assertEquals(HttpStatus.OK, response.get("status"));
        assertEquals(1, response.get("page"));
        assertEquals(0, response.get("current_page"));
        assertEquals(0, response.get("total"));
        assertEquals(Collections.emptyList(), response.get("data"));
    }

    @Test
    @DisplayName("should return all files with correct pagination and status")
    void getAllFilesWithCorrectPaginationAndStatus() {
        List<FileMeta> fileMetaList = Collections.singletonList(fileMeta);
        Page<FileMeta> fileMetaPage = new PageImpl<>(fileMetaList);
        when(fileMetaService.list(anyLong(), anyInt())).thenReturn(fileMetaPage);

        ResponseEntity<Map<Object, Object>> response = fileController.getAllFiles(projectId, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get("page"));
        assertEquals(0, response.getBody().get("current_page"));
        assertEquals(1, response.getBody().get("total"));
        assertEquals(fileMetaList, response.getBody().get("data"));
    }
}