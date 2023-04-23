package com.follabj_be.follabj_be;


import com.follabj_be.follabj_be.entity.Role;
import com.follabj_be.follabj_be.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FollabJBeApplication {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private static RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(FollabJBeApplication.class, args);
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        FollabJBeApplication.roleRepository = roleRepository;
    }
}
