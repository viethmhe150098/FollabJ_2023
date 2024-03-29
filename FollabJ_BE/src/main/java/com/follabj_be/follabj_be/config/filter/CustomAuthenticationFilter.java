package com.follabj_be.follabj_be.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;
import com.follabj_be.follabj_be.repository.ProjectRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, ProjectRepository projectRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("User with username: {} and password: {} is login", username, password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        //while authentication with username and password successfully do the logic below
        User user = (User) authResult.getPrincipal(); //get user
        //create token
        Algorithm algorithm = Algorithm.HMAC256("viet".getBytes());
        String access_token = JWT.create() //create access token
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))//10m
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                //get user roles
                .sign(algorithm);

        String refresh_token = JWT.create() //create refresh token
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))//60m
                .sign(algorithm);
        //send token back to user as JSON
        Long user_id = Long.parseLong(userRepository.findAppUserByEmail(user.getUsername()).get().getId().toString());
        Map<Object, Object> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        tokens.put("email", user.getUsername());
        tokens.put("id", user_id);
        tokens.put("status", String.valueOf(userRepository.findAppUserByEmail(user.getUsername()).get().getStatus()));
        tokens.put("isLeaderOf", projectRepository.getProjectIdByLeaderID(user_id));
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(401);
        Map<String, String> error = new HashMap<>();
        error.put("statusCode", HttpStatus.UNAUTHORIZED.toString());
        error.put("message", CustomErrorMessage.WRONG_CREDENTIAL.getCode() + ":" + CustomErrorMessage.WRONG_CREDENTIAL.getMessage());
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

}
