package com.follabj_be.follabj_be.service.dependency;

import com.follabj_be.follabj_be.exception.NoTypeException;

public interface MeetingInterface {
    String generateRoomId(Long project_id, int type) throws NoTypeException;
}
