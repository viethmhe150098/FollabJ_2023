package com.follabj_be.follabj_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NoteDTO {

    private Long id;

    private String title;

    private String content;

    private Date createdDate;

    private Date updatedDate;

    private UserDTO creator;

}
