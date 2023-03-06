package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.entity.MeetingType;
import com.follabj_be.follabj_be.repository.MeetingTypeRepository;
import com.follabj_be.follabj_be.service.MeetingTypeInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class MeetingTypeService implements MeetingTypeInterface {
    private final MeetingTypeRepository meetingTypeRepository;

    public MeetingTypeService(MeetingTypeRepository meetingTypeRepository) {
        this.meetingTypeRepository = meetingTypeRepository;
    }

    @Override
    @Bean
    public void init() {
        if (meetingTypeRepository.findAll().isEmpty()) {
            meetingTypeRepository.save(new MeetingType(1, "INSTANT"));
            meetingTypeRepository.save(new MeetingType(2, "LATER"));
        }
    }
}
