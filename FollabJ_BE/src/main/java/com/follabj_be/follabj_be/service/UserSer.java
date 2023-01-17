package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.entity.User;
import com.follabj_be.follabj_be.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSer {

    @Autowired
    private UserRepo userRepo;
    public User findById(Long id){
        User user = userRepo.findById(id).orElseThrow();
        return user;
    }
}
