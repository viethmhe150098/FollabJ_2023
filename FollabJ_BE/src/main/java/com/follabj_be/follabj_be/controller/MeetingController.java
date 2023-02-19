package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.exception.NoTypeException;
import com.follabj_be.follabj_be.service.MeetingService;
import com.follabj_be.follabj_be.utils.TokenServerAssistant;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class MeetingController {
    private final long APP_ID = Long.parseLong(System.getenv("APP_ID"));
    private final String SECRET_KEY = System.getenv("SECRET_KEY");
    private final MeetingService meetingService;
    @PostMapping("/project/{p_id}/meeting")
    public ResponseEntity<Map<Object, Object>> getRoomCode(@PathVariable Long p_id, @RequestParam int type) throws NoTypeException {
        Map<Object, Object> res = new HashMap<>();
        res.put("roomCode", meetingService.generateRoomId(p_id, type));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/project/meeting")
    public Map<Object, Object> getToken (@RequestParam String channelId, @RequestParam String userId, @RequestParam int expired_ts){
        Map<Object, Object> payloadData = new HashMap<>();
        payloadData.put("roomId", channelId);
        Map<Object, Object> privilege = new HashMap<>();
        privilege.put(TokenServerAssistant.PrivilegeKeyLogin, TokenServerAssistant.PrivilegeEnable);
        privilege.put(TokenServerAssistant.PrivilegeKeyPublish, TokenServerAssistant.PrivilegeDisable);
        payloadData.put("privilege", privilege);
        payloadData.put("stream_id_list", null);
        String payload = payloadData.toString();
        TokenServerAssistant.VERBOSE = false;
        TokenServerAssistant.TokenInfo token = TokenServerAssistant.generateToken04(APP_ID,  userId, SECRET_KEY, expired_ts, payload);
        System.out.println(token.data);
        Map<Object, Object> response = new HashMap<>();
        response.put("token", token.data);
        return response;
    }
}
