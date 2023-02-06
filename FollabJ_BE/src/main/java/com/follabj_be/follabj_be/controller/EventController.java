package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.entity.Event;
import com.follabj_be.follabj_be.requestModel.EventRequest;
import com.follabj_be.follabj_be.service.dependency.EventInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EventController {
    private final EventInterface eventInterface;

    public EventController(EventInterface eventInterface) {
        this.eventInterface = eventInterface;
    }

    @PostMapping("/event")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<Map<String, Object>> addTask(@RequestBody EventRequest eventRequest){
        Map<String, Object> response = new HashMap<>();
        if(eventRequest!=null) {
            eventInterface.saveEvent(new Event(
                    eventRequest.getTitle(),
                    eventRequest.getDes(),
                    eventRequest.getStart(),
                    eventRequest.getEnd())
            );

            response.put("event", eventRequest);
            response.put("status", "200");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else{
            response.put("content", "No content");
            response.put("status", "204");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllEvent(){
        List<Event> events = eventInterface.getAllEvent();
        Map<String, Object> response = new HashMap<>();
        if(!events.isEmpty()) {
            response.put("events", events);
            response.put("status", "200");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("content", "No content");
            response.put("status", "204");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }
}
