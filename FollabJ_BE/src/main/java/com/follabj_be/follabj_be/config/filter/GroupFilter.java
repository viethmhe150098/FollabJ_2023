package com.follabj_be.follabj_be.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;
import com.follabj_be.follabj_be.exception.GroupPermissionException;
import com.follabj_be.follabj_be.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Slf4j
public class GroupFilter extends GenericFilterBean {
    private final ProjectRepository projectRepository;

    public GroupFilter(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if(request.getRequestURL().toString().contains("/project")){
            //String param_p_id = request.getRequestURI().substring(9,11);
            String[] urlParts = request.getRequestURI().split("/");
            String param_p_id = urlParts[2];
            Long project_id = Long.valueOf(param_p_id);
            String authorizationHeader = request.getHeader("AUTHORIZATION");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String access_token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("viet".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    //decoded token from header
                    DecodedJWT decodedJWT = verifier.verify(access_token);
                    String username = decodedJWT.getSubject();
                    Project p = projectRepository.findById(project_id).orElseThrow(() -> new ObjectNotFoundException("Not found project", project_id.toString()));
                    if(p.getStatus().equals(Project.ProjectStatus.ACTIVE)) {
                        List<AppUser> members_list = projectRepository.getMembersById(project_id);
                        Optional<AppUser> member = members_list.stream().filter(user -> user.getEmail().equals(username)).findAny();
                        member.orElseThrow(() -> new GroupPermissionException(CustomErrorMessage.NO_PERMISSION));
                        filterChain.doFilter(request, response);
                    }else{
                        response.setStatus(401);
                        Map<String, String> tokens = new HashMap<>();
                        tokens.put("message", "Group is deactivate");
                        response.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error logging in: {}", e.getMessage());
                    response.setStatus(401);
                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("error", e.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
