package com.follabj_be.follabj_be.entity;

import lombok.*;
import org.aspectj.apache.bcel.ExceptionConstants;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 30)
    private String title;

    @Size(max = 100)
    private String description;

    @Size(max = 50)
    private String label;
    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private Date endDate;

    @Min(1)
    @Max(3)
    @Column(columnDefinition = "integer default 1")
    private int statusId;

    private int columnPosition;

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "task_assignee",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AppUser> assigneeList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @AssertTrue(message = "Date is null or Unsupported Date Format")
    // Any method name is ok als long it begins with `is`
    private boolean isDateValid() {
        return this.startDate != null && this.endDate != null;
    }

    @AssertTrue(message = "Field `endDate` value must be after  field `startDate` value")
    // Any method name is ok als long it begins with `is`
    private boolean isEndDateValid() {
        if(isDateValid()) {
            return this.endDate.after(this.startDate);
        } else
            return isDateValid();
    }
}
