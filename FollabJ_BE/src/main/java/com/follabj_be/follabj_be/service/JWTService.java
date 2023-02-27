package com.follabj_be.follabj_be.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Role;
import com.follabj_be.follabj_be.exception.JwtException;
import com.follabj_be.follabj_be.service.dependency.JWTInterface;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class JWTService implements JWTInterface {
    private final UserService userService;

    public JWTService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Map<String, String> getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            try{
                String refreshToken = authorizationHeader.substring("Bearer ".length()); //get token after bearer
                Algorithm algorithm = Algorithm.HMAC256("viet".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                AppUser user = userService.getUserByEmail(username);
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ 10*60*1000)) //10m
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access token", accessToken);
                tokens.put("refresh token", refreshToken);
                return tokens;

            }catch (JwtException e){
                response.setHeader("error", e.getMessage());
                response.setStatus(403);
                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                return error;
            }

        }else{
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
