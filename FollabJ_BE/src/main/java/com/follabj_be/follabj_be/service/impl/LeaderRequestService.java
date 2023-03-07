package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.dto.LeaderRequestDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.LeaderRequest;
import com.follabj_be.follabj_be.repository.LeaderRequestRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import com.follabj_be.follabj_be.service.LeaderRequestInterface;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LeaderRequestService implements LeaderRequestInterface {
    private final LeaderRequestRepository leaderRequestRepository;
    private final UserRepository userRepository;
    @Override
    public boolean isPendingRequest(Long u_id) {
        return leaderRequestRepository.requestIsPending(u_id);
    }

    @Override
    public void updateRequestStatus(Long req_id, int status) {

    }

    @Override
    public String saveRequest(LeaderRequestDTO leaderRequestDTO) {
        Long user_id = leaderRequestDTO.getU_id();
        if(isPendingRequest(user_id)){
            return "You have 1 request waiting to be approved, please wait";
        }else{
            AppUser user = userRepository.findById(user_id).orElseThrow(()-> new ObjectNotFoundException("Not found user", user_id.toString()));
            LeaderRequest l = new LeaderRequest(user, leaderRequestDTO.getU_fullname(), leaderRequestDTO.getU_id_number());
            leaderRequestRepository.save(l);
            return "The request has been sent to the administrator";
        }
    }
}
