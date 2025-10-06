package com.example.Task.Management.service;

import com.example.Task.Management.entity.UserEntity;
import com.example.Task.Management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public List<UserEntity> viewAllUsers()   //view all users
    {
        return userRepository.findAll();
    }

    public ResponseEntity<?> addNewUser(UserEntity user) {

        if(userRepository.existsByEmail(user.getEmail()))
        {
           return new ResponseEntity<>("User already exists", HttpStatus.FORBIDDEN);
        }
            userRepository.save(user);  //saving users
            return new ResponseEntity<>("User Created",HttpStatus.CREATED);
    }




    public UserEntity getUser(Long id)//
    {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")); //If no user found
    }
}


