package com.follabj_be.follabj_be.requestModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventRequest {
    private String title;
    private String des;
    private String start;
    private String end;
}
