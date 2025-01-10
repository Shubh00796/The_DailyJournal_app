package com.sprinboot2025.demo.controller;

import com.sprinboot2025.demo.entity.UserEntry;
import com.sprinboot2025.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

     @Autowired
     private UserService  userService;
    @GetMapping("/healthcheck")
    public String healthCheck(){
        return "Ok" ;
    }

    @PostMapping
    public ResponseEntity<UserEntry> saveUser(@RequestBody UserEntry userEntry) {
        UserEntry savedUser = userService.saveUser(userEntry);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
