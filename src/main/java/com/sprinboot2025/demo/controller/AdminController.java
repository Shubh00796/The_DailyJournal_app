package com.sprinboot2025.demo.controller;

import com.sprinboot2025.demo.entity.UserEntry;
import com.sprinboot2025.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService serivce;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<UserEntry> allUsers = serivce.getAllUsers();
        if (allUsers != null && !allUsers.isEmpty()) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody UserEntry user) {
        serivce.saveAdmin(user);
    }
}
