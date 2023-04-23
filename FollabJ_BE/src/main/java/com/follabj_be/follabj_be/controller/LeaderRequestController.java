package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.LeaderRequestDTO;
import com.follabj_be.follabj_be.entity.LeaderRequest;
import com.follabj_be.follabj_be.service.impl.LeaderRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class LeaderRequestController {
    private final LeaderRequestService leaderRequestService;
    @GetMapping("/admin/request")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<Object, Object>> getIsPending( @RequestParam(required=false, defaultValue="PENDING") String status){
        Map<Object, Object> res = new HashMap<>();
        LeaderRequest.requestStatus requestStatus = LeaderRequest.requestStatus.valueOf(status);
        List<LeaderRequestDTO> pageRequest = leaderRequestService.getListRequest(requestStatus);
        res.put("status", HttpStatus.OK);
        res.put("content", pageRequest);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/admin/request/{req_id}")
    public ResponseEntity<Map<Object, Object>> updateRequestStatus(@PathVariable Long req_id, @RequestParam int status){
        Map<Object, Object> res = new HashMap<>();


        if(status == 1) {
            leaderRequestService.updateRequestStatus(req_id, status);
            res.put("message", "Update status to accept for request id "+ req_id);
            res.put("status", HttpStatus.OK);
        }else if (status == 2){

            leaderRequestService.updateRequestStatus(req_id, status);
            res.put("status", HttpStatus.OK);
            res.put("message", "Update status to decline for request id "+ req_id);
        }else {
            res.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            res.put("message", "Invalid status code");
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
