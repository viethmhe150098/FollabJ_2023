package com.follabj_be.follabj_be.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.follabj_be.follabj_be.entity.FileMeta;
import com.follabj_be.follabj_be.service.impl.FileMetaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class FileController {
    private FileMetaService fileMetaService;

    @GetMapping("/project/{project_id}")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<Map<Object, Object>> getAllFiles(@PathVariable Long project_id, @RequestParam int page) {
        Map<Object, Object> response = new HashMap<>();
        Page<FileMeta> listFile = fileMetaService.list(project_id, page);
        response.put("status", HttpStatus.OK);
        response.put("page", page);
        response.put("current_page", listFile.getNumber());
        response.put("total", listFile.getTotalElements());
        response.put("data", listFile);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/project/{project_id}/upload")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public FileMeta upload(
            @RequestParam("file") MultipartFile file, @PathVariable Long project_id, @RequestParam Long u_id) throws IOException {
        return fileMetaService.upload(file, project_id, u_id);
        //return "Upload Success";
    }

    @GetMapping("/project/{project_id}/download/{id}")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    @ResponseBody
    public HttpEntity<byte[]> download(@PathVariable Long project_id, @PathVariable Long id) throws
            IOException {

        S3Object s3Object = fileMetaService.download(id, project_id);
        String contentType = s3Object.getObjectMetadata().getContentType();
        var bytes = s3Object.getObjectContent().readAllBytes();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(contentType));
        header.setContentLength(bytes.length);

        return new HttpEntity<>(bytes, header);
    }
}
