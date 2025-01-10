package com.sprinboot2025.demo.controller;

import com.sprinboot2025.demo.entity.UserEntry;
import com.sprinboot2025.demo.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    //    @PostMapping("/save")
//    public ResponseEntity<UserEntry> saveUserNew(@RequestBody UserEntry userEntry) {
//        try {
//            UserEntry savedUser = userService.saveUser(userEntry);
//            return ResponseEntity.ok(savedUser);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(null);
//        }
//    }
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntry user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();



        // Find the user by username
        Optional<UserEntry> userInDbOptional = userService.getUserName(userName);
        if (userInDbOptional.isPresent()) {
            UserEntry userInDb = userInDbOptional.get(); // Retrieve the actual UserEntry object

            // Update fields
            userInDb.setUsername(user.getUsername());

            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                userInDb.setPassword(encoder.encode(user.getPassword()));
            }

            // Save the updated user
            userService.saveUser(userInDb);

            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        }
        // Return 404 if user not found
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntry> getUserById(@PathVariable String id) {
        Optional<UserEntry> user = userService.getUserById(new ObjectId(id));
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserEntry> getUserByEmail(@PathVariable String email) {
        Optional<UserEntry> user = userService.getUserByEmail(email);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id) {
        try {
            userService.deleteUserById(new ObjectId(id));
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserEntry>> getAllUsers() {
        List<UserEntry> users = userService.getAllUsers();
        return users.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(users, HttpStatus.OK);
    }
}
