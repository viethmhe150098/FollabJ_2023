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
    @NotEmpty(message = "Not empty")
    @Max(value = 30, message = "Not match pattern")
    @Min(value = 1, message = "Not match pattern")
    private String title;
    @NotEmpty(message = "Not empty")
    @Max(value = 100, message = "Not match pattern")
    private String description;

    @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]", message = "Not match pattern")
    @NotEmpty
    private Date startDate;

    @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]", message = "Not match pattern")
    @NotEmpty
    private Date endDate;

    @NotEmpty
    @Pattern(regexp = "[0-9]+", message = "Not match pattern")
    private Long projectId;
    @NotEmpty
    private List<UserDTO> participantList;
}
