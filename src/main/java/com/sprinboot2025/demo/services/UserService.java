package com.sprinboot2025.demo.services;

import com.sprinboot2025.demo.entity.JournalEntry;
import com.sprinboot2025.demo.entity.UserEntry;
import com.sprinboot2025.demo.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static  final PasswordEncoder encoder = new BCryptPasswordEncoder();
//    public UserEntry saveUser(UserEntry userEntry) {
//        return userRepository.save(userEntry);
//    }

    public UserEntry saveUser(UserEntry userEntry) {
        try {
            // Validate userEntry fields (e.g., password should not be null)
            if (userEntry.getPassword() == null || userEntry.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be null or empty");
            }

            // Encode the password
            userEntry.setPassword(encoder.encode(userEntry.getPassword()));

            // Assign default roles if not provided
            if (userEntry.getRoles() == null || userEntry.getRoles().isEmpty()) {
                userEntry.setRoles(Arrays.asList("User"));
            }

            // Save the user to the repository
            return userRepository.save(userEntry);

        } catch (Exception e) {
            // Log the error with context
            log.error("Error occurred while saving user: {}", e.getMessage(), e);

            // Throw a custom exception or rethrow the original exception
            throw new RuntimeException("Unable to save user", e);
        }
    }

//
//    public UserEntry updateUser(UserEntry userEntry) {
//        if (userEntry.getId() != null && userRepository.existsById(userEntry.getId())) {
//            UserEntry existingUser = userRepository.findById(userEntry.getId()).orElseThrow(() -> new RuntimeException("User not found"));
//            existingUser.setUsername(userEntry.getUsername());
//            existingUser.setEmail(userEntry.getEmail());
//            existingUser.setPassword(userEntry.getPassword());
//            existingUser.setActive(userEntry.isActive());
//            return userRepository.save(existingUser);
//        }
//        return null;
//    }

    public Optional<UserEntry> getUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntry> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<UserEntry> getUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserEntry> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }



    public UserEntry saveAdmin(UserEntry userEntry) {
        userEntry.setPassword(encoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("User","ADMIN"));
        return userRepository.save(userEntry);
    }
}
