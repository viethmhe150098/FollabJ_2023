package com.follabj_be.follabj_be.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Event{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Column(name = "description")
    private String describe;
    private String start;
    private String end;
    private static final String status = "0";

    public Event(String title, String describe, String start, String end) {
        this.title = title;
        this.describe = describe;
        this.start = start;
        this.end = end;
    }
}
