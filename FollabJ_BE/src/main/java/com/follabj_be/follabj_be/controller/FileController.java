package com.follabj_be.follabj_be.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.follabj_be.follabj_be.dto.FileDTO;
import com.follabj_be.follabj_be.entity.FileMeta;
import com.follabj_be.follabj_be.service.impl.FileMetaService;
import com.follabj_be.follabj_be.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class FileController {
    private FileMetaService fileMetaService;

    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/project/{project_id}/files")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<Map<Object, Object>> getAllFiles(@PathVariable Long project_id) {
        Map<Object, Object> response = new HashMap<>();
        //Page<FileMeta> listFile = fileMetaService.list(project_id, page);
        List<FileMeta> listFile = fileMetaService.listAllByProject(project_id);
        List<FileDTO> fileDTOList = new ArrayList<>();
        for (FileMeta file: listFile) {
            FileDTO fileDTO = modelMapper.map(file, FileDTO.class);
            fileDTOList.add(fileDTO);
        }
        response.put("status", HttpStatus.OK);
//        response.put("page", page);
//        response.put("current_page", listFile.getNumber());
//        response.put("total", listFile.getTotalElements());
        response.put("data", fileDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/project/{project_id}/files/upload")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public FileDTO upload(
            Authentication authentication, @RequestParam("file") MultipartFile file, @PathVariable Long project_id) throws IOException {
        Long u_id = getUserId(authentication);
        FileMeta fileMeta =  fileMetaService.upload(file, project_id, u_id);
        FileDTO fileDTO = modelMapper.map(fileMeta, FileDTO.class);
        return fileDTO;
        //return "Upload Success";
    }

    @GetMapping("/project/{project_id}/files/download/{id}")
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

    public Long getUserId(Authentication authentication) {
        return userService.getUserByEmail(authentication.getPrincipal().toString()).getId();
    }
}
