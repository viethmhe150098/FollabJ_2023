package com.follabj_be.follabj_be.controller.rtcToken;

import com.follabj_be.follabj_be.utils.TokenServerAssistant;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ZegoRTCToken {
    private final long appId = Long.parseLong(System.getenv("APP_ID"));
    private final String serverSecret = System.getenv("APP_SECRET");


    @GetMapping(path = "/rtctoken")
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
        TokenServerAssistant.TokenInfo token = TokenServerAssistant.generateToken04(appId,  userId, serverSecret, expired_ts, payload);
        System.out.println(token.data);
        Map<Object, Object> response = new HashMap<>();
        response.put("token", token.data);
        return response;
    }

}
