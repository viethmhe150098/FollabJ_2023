package com.follabj_be.follabj_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "file_meta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "FILE_NAME", nullable = false)
    private String fileName;
    @Column(name = "FILE_PATH", nullable = false)
    private String filePath;
    @Column(nullable = false)
    private String uploadDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "p_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "uploadBy", referencedColumnName = "id")
    private AppUser user;

    public FileMeta(String fileName, String filePath, String uploadDate, Project project, AppUser user) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.uploadDate = uploadDate;
        this.project = project;
        this.user = user;
    }


}
