package com.follabj_be.follabj_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileDTO {

    private Long id;
    private String fileName;

    private String filePath;

    private String uploadDate;

    private UserDTO user;
}
