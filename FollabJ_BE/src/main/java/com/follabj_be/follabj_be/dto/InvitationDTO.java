package com.follabj_be.follabj_be.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Project;

import javax.persistence.*;

public class InvitationDTO {

    private Long id;

    private UserDTO receiver;
    private int status;

    private ProjectDTO project;


}
