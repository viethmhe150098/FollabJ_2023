package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.dto.LeaderRequestDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.LeaderRequest;
import com.follabj_be.follabj_be.repository.LeaderRequestRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import com.follabj_be.follabj_be.service.EmailSender;
import com.follabj_be.follabj_be.service.LeaderRequestInterface;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LeaderRequestService implements LeaderRequestInterface {
    private final LeaderRequestRepository leaderRequestRepository;
    private final UserRepository userRepository;
    private final BuildEmail buildEmail;
    private final EmailSender emailSender;
    @Override
    public boolean isPendingRequest(Long u_id) {
        return leaderRequestRepository.existsByUserIdAndStatus(u_id, LeaderRequest.requestStatus.PENDING);
    }

    @Override
    public void updateRequestStatus(Long req_id, int status) {
        LeaderRequest l = leaderRequestRepository.findById(req_id).orElseThrow(()->new ObjectNotFoundException("Not found request", req_id.toString()));
        if(status == 1){
            l.setStatus(LeaderRequest.requestStatus.ACCEPT);
            userRepository.promoteLeader(l.getUser().getId());
            //emailSender.sendEmail(l.getUser().getEmail(), buildEmail.becomeLeader(l.getUser().getUsername()));
        }else{

            l.setStatus(LeaderRequest.requestStatus.REJECT);
        }
        leaderRequestRepository.save(l);
    }

    @Override
    public String saveRequest(LeaderRequestDTO leaderRequestDTO) {
        Long user_id = leaderRequestDTO.getUser().getId();
        if(isPendingRequest(user_id)){
            return "You have 1 request waiting to be approved, please wait";
        }else{
            AppUser user = userRepository.findById(user_id).orElseThrow(()-> new ObjectNotFoundException("Not found user", user_id.toString()));
            LeaderRequest l = new LeaderRequest();
            l.setUser(user);
            l.setMessage(leaderRequestDTO.getMessage());

            leaderRequestRepository.save(l);
            return "The request has been sent to the administrator";
        }
    }

    @Override
    public Page<LeaderRequest> getListRequest(int page) {
        Pageable paging = PageRequest.of(page,7);
        return leaderRequestRepository.getPendingRequest(paging);
    }

    @Override
    public LeaderRequest getRequestByUserId(Long u_id) {
        return leaderRequestRepository.findByUserId(u_id);
    }

    @Override
    public Page<LeaderRequestDTO> getListRequest(int page, LeaderRequest.requestStatus status) {
        return null;
    }

    @Override
    public List<LeaderRequestDTO> getListRequest(LeaderRequest.requestStatus status) {
//        Pageable paging = PageRequest.of(page,7);
//        return leaderRequestRepository.getRequestsByStatus(status, paging);
        List<LeaderRequestDTO> leaderRequests= leaderRequestRepository.getLeaderRequestByStatus(status).stream().map(leaderRequest
                -> new LeaderRequestDTO(leaderRequest.getId(), leaderRequest.getMessage(),
                new UserDTO(leaderRequest.getUser().getId(),
                        leaderRequest.getUser().getEmail(),
                        leaderRequest.getUser().getUsername()),leaderRequest.getStatus())).toList();
        return leaderRequests;
    }


}
