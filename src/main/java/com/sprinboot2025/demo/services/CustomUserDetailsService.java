package com.sprinboot2025.demo.services;

import com.sprinboot2025.demo.entity.UserEntry;
import com.sprinboot2025.demo.repo.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntry user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));



        if (user != null) {
            UserDetails userdeatils = User.builder().
                    username(user.getUsername()).
                    password(user.getPassword()).
                    roles(user.getRoles().toArray(new String[0])).
                    build();
            return userdeatils;


        }
        throw new UsernameNotFoundException("user not found with usernmae" + username);
//        List<String> roles = user.getRoles(); // Assuming getRoles() returns a List<String>
//        String[] rolesArray = roles != null ? roles.toArray(new String[0]) : new String[0];


    }
}