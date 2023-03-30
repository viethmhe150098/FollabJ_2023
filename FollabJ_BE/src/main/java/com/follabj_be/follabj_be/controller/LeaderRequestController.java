package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.entity.LeaderRequest;
import com.follabj_be.follabj_be.service.impl.LeaderRequestService;
import lombok.AllArgsConstructor;
import org.apache.http.protocol.HTTP;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class LeaderRequestController {
    private final LeaderRequestService leaderRequestService;
    @GetMapping("/admin/request")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<Object, Object>> getIsPending(@RequestParam int page, @RequestParam Optional<LeaderRequest.requestStatus> status){
        Map<Object, Object> res = new HashMap<>();
        Page<LeaderRequest> pageRequest = leaderRequestService.getListRequest(page, status.orElse(LeaderRequest.requestStatus.PENDING));
        res.put("status", HttpStatus.OK);
        res.put("content", pageRequest);
        res.put("curr_Page", pageRequest.getNumber());
        res.put("total", pageRequest.getTotalPages());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/admin/request/{req_id}")
    public ResponseEntity<Map<Object, Object>> updateRequestStatus(@PathVariable Long req_id, @RequestParam int status){
        Map<Object, Object> res = new HashMap<>();
        leaderRequestService.updateRequestStatus(req_id, status);
        res.put("status", HttpStatus.OK);
        if(status == 1) {
            res.put("message", "Update status to accept for request id "+ req_id);
        }else{
            res.put("message", "Update status to decline for request id "+ req_id);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
