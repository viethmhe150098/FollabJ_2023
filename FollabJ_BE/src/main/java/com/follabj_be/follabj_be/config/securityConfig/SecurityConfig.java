package com.follabj_be.follabj_be.config.securityConfig;

import com.follabj_be.follabj_be.config.filter.CustomAuthenticationFilter;
import com.follabj_be.follabj_be.config.filter.CustomAuthorizationFilter;
import com.follabj_be.follabj_be.repository.UserRepository;
import com.follabj_be.follabj_be.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
                    config.addAllowedMethod(HttpMethod.PUT);
                    config.addAllowedMethod(HttpMethod.DELETE);
                    return config;
                });
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .addFilter(new CustomAuthenticationFilter(authenticationManager(), userRepository));
        http
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.logout();
    }
}
