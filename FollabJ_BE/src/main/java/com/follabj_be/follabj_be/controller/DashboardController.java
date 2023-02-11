package com.follabj_be.follabj_be.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.follabj_be.follabj_be.entity.FileMeta;
import com.follabj_be.follabj_be.service.FileMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class DashboardController {
    @Autowired
    private FileMetaService fileMetaService;

    @GetMapping
    public Map<Object, Object> getAllFiles(Long project_id){
        Map<Object, Object> response = new HashMap<>();
        List<FileMeta> listFile = fileMetaService.list();
        response.put("status", HttpStatus.OK);
        response.put("data", listFile);
        return response;
    }
    @PostMapping("/upload")
    public String upload(
            @RequestParam("file") MultipartFile file) throws IOException {
        fileMetaService.upload(file);
        return "Upload Success";
    }

    @GetMapping("/download/{id}")
    @ResponseBody
    public HttpEntity<byte[]> download(@PathVariable Long id) throws
            IOException {

        S3Object s3Object = fileMetaService.download(id);
        String contentType = s3Object.getObjectMetadata().getContentType();
        var bytes = s3Object.getObjectContent().readAllBytes();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(contentType));
        header.setContentLength(bytes.length);

        return new HttpEntity<byte[]>(bytes, header);
    }

    @DeleteMapping("/{id}")
    public Map<Object, Object> delete(@PathVariable Long id){
        Map<Object, Object> res = new HashMap<>();
        fileMetaService.delete(id);
        res.put("message", "Delete successfully");
        res.put("status", HttpStatus.OK);
        return res;
    }
}
