package com.follabj_be.follabj_be.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateEventDTO {
    @NotBlank(message = "Title is mandatory")
    @Size(max=30)
    private String title;
    @Size(max=100)
    private String description;

    private Date startDate;

    private Date endDate;

    private Long projectId;
    @NotEmpty
    private List<UserDTO> participantList;

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
