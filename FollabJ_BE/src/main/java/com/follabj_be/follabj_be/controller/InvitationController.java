package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.UpdateStatusDTO;
import com.follabj_be.follabj_be.service.InvitationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping("/invitation")
    public ResponseEntity<String> updateStatus(@RequestBody UpdateStatusDTO updateStatusDTO){
        invitationService.updateStatus(updateStatusDTO.getStatus(), updateStatusDTO.getI_id());
        if(updateStatusDTO.getStatus()==0){
            return new ResponseEntity<>("You have declined this invitation", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("You have accepted this invitation", HttpStatus.OK);
        }
    }
}
