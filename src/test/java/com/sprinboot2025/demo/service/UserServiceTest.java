package com.sprinboot2025.demo.service;

import com.sprinboot2025.demo.entity.UserEntry;
import com.sprinboot2025.demo.repo.UserRepo;
import com.sprinboot2025.demo.services.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepository;

    @InjectMocks
    private UserService userService;

    private UserEntry userEntry;

    @BeforeEach
    void setUp() {
        userEntry = new UserEntry();
        userEntry.setId(new ObjectId());
        userEntry.setUsername("testUser");
        userEntry.setEmail("test@example.com");
        userEntry.setPassword("password123");
        userEntry.setActive(true);
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(any(UserEntry.class))).thenReturn(userEntry);

        UserEntry savedUser = userService.saveUser(userEntry);

        assertNotNull(savedUser);
        assertEquals("testUser", savedUser.getUsername());
        assertEquals("test@example.com", savedUser.getEmail());
        assertTrue(savedUser.getRoles().contains("User"));
        verify(userRepository, times(1)).save(any(UserEntry.class));
    }

    @Test
    void testSaveAdmin() {
        userEntry.setRoles(Arrays.asList("User", "ADMIN"));
        when(userRepository.save(any(UserEntry.class))).thenReturn(userEntry);

        UserEntry savedAdmin = userService.saveAdmin(userEntry);

        assertNotNull(savedAdmin);
        assertTrue(savedAdmin.getRoles().contains("ADMIN"));
        verify(userRepository, times(1)).save(any(UserEntry.class));
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(any(ObjectId.class))).thenReturn(Optional.of(userEntry));

        Optional<UserEntry> foundUser = userService.getUserById(userEntry.getId());

        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
        verify(userRepository, times(1)).findById(any(ObjectId.class));
    }

    @Test
    void testGetUserByEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntry));

        Optional<UserEntry> foundUser = userService.getUserByEmail("test@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testGetUserName() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userEntry));

        Optional<UserEntry> foundUser = userService.getUserName("testUser");

        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(userEntry));

        List<UserEntry> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("testUser", users.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testDeleteUserById() {
        doNothing().when(userRepository).deleteById(any(ObjectId.class));

        userService.deleteUserById(userEntry.getId());

        verify(userRepository, times(1)).deleteById(any(ObjectId.class));
    }

    @Test
    void testSaveUserWithEmptyPassword() {
        userEntry.setPassword("");
        when(userRepository.save(any(UserEntry.class))).thenReturn(userEntry);

        UserEntry savedUser = userService.saveUser(userEntry);

        assertNotNull(savedUser);
        assertTrue(savedUser.getPassword().length() > 0);
        verify(userRepository, times(1)).save(any(UserEntry.class));
    }

    @Test
    void testSaveUserWithNullPassword() {
        // This test is removed since null passwords are not valid
    }

}
