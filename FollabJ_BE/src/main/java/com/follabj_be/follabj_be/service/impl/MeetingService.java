package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.entity.Meeting;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;
import com.follabj_be.follabj_be.exception.NoTypeException;
import com.follabj_be.follabj_be.repository.EventRepository;
import com.follabj_be.follabj_be.repository.MeetingRepository;
import com.follabj_be.follabj_be.repository.MeetingTypeRepository;
import com.follabj_be.follabj_be.repository.ProjectRepository;
import com.follabj_be.follabj_be.service.MeetingInterface;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MeetingService implements MeetingInterface {
    private final MeetingRepository meetingRepository;
    private final ProjectRepository projectRepository;
    private final MeetingTypeRepository meetingTypeRepository;
    private final EventRepository eventRepository;

    public MeetingService(MeetingRepository meetingRepository, ProjectRepository projectRepository, MeetingTypeRepository meetingTypeRepository, EventRepository eventRepository) {
        this.meetingRepository = meetingRepository;
        this.projectRepository = projectRepository;
        this.meetingTypeRepository = meetingTypeRepository;
        this.eventRepository = eventRepository;
    }


    @Override
    public String generateRoomId(Long project_id, int type) throws NoTypeException {
        Project p = projectRepository.findById(project_id).orElseThrow(() -> new ObjectNotFoundException("Not found project", project_id.toString()));
        String project_name = p.getName();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String create_at = dtf.format(now);
        String encoded = project_name + create_at;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] roomCode = digest.digest(encoded.getBytes(StandardCharsets.UTF_8));

            if (type == 1) {
                String meeting_id = bytesToHex(roomCode, 3);
                meetingRepository.save(new Meeting(meeting_id, p, meetingTypeRepository.findById(1).get()));
                return meeting_id;
            }
            if (type == 2) {
                String meeting_id = bytesToHex(roomCode, 4);
                meetingRepository.save(new Meeting(meeting_id, p, meetingTypeRepository.findById(2).get()));
                return meeting_id;
            } else {
                throw new NoTypeException(CustomErrorMessage.TYPE01);

            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] hash, int number) {
        StringBuilder hexString = new StringBuilder(8);
        for (int i = 0; i < number; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
