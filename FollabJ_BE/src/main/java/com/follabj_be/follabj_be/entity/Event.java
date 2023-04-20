package com.follabj_be.follabj_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Title is mandatory")
    @Size(max=30)
    private String title;

    @Size(max=100)
    private String description;

    private Date startDate;

    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "event_participant",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @NotEmpty
    private List<AppUser> participantList;


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
