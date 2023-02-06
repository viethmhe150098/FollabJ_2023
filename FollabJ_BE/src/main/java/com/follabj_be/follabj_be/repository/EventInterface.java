package com.follabj_be.follabj_be.service.dependency;

import com.follabj_be.follabj_be.entity.Event;
import com.follabj_be.follabj_be.requestModel.EventRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EventInterface {
    Event saveEvent(Event event);
    List<Event> getAllEvent();
}
