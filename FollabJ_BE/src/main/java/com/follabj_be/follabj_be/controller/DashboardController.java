package com.follabj_be.follabj_be.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.follabj_be.follabj_be.service.FileMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class DashboardController {
    @Autowired
    private FileMetaService fileMetaService;
    @PostMapping("upload")
    public String upload(
            @RequestParam("file") MultipartFile file) throws IOException {
        fileMetaService.upload(file);
        return "Upload Success";
    }

    @GetMapping("download/{id}")
    @ResponseBody
    public HttpEntity<byte[]> download(Model model, @PathVariable Long id, HttpServletResponse response) throws
            IOException {

        S3Object s3Object = fileMetaService.download(id);
        String contentType = s3Object.getObjectMetadata().getContentType();
        var bytes = s3Object.getObjectContent().readAllBytes();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(contentType));
        header.setContentLength(bytes.length);

        return new HttpEntity<byte[]>(bytes, header);
    }
}
