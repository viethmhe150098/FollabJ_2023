package com.follabj_be.follabj_be.dto;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectDTO {

    private Long id;

    private String name;
    private String des;
    private String createdDate;
    private String status;
    private UserDTO leader;

    private List<UserDTO> members;
    public ProjectDTO(Long id, String name, UserDTO leader) {
        this.id = id;
        this.name = name;
        this.leader = leader;
    }

    public ProjectDTO(Long id, String name, String des, UserDTO leader, List<UserDTO> members) {
        this.id = id;
        this.name = name;
        this.des = des;
        this.leader = leader;
        this.members = members;
    }
}
