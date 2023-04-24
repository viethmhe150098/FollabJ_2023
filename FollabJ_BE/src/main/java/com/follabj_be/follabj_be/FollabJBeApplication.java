package com.follabj_be.follabj_be;

import com.follabj_be.follabj_be.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FollabJBeApplication {
    @Autowired
    private RoleRepository roleRepository;
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(FollabJBeApplication.class, args);
    }

    public void initRole() {
        roleRepository.save(new Role(0L, "INACTIVE"));
        roleRepository.save(new Role(1L, "ACTIVE_USER"));
        roleRepository.save(new Role(2L, "LEADER"));
        roleRepository.save(new Role(3L, "ADMIN"));
    }
}
