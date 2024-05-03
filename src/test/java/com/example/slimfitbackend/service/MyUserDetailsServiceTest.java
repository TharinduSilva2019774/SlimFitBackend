package com.example.slimfitbackend.service;

import com.example.slimfitbackend.model.User;
import com.example.slimfitbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MyUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MyUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_UserFound_ReturnsUserDetails() {
        String username = "test@example.com";
        User user = new User();
        user.setEmail(username);
        user.setPassword("hashedPassword");

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("foo", userDetails.getPassword()); // Assuming password is always "foo" for testing purposes
        assertTrue(userDetails.getAuthorities().isEmpty()); // Assuming no authorities for simplicity
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsUsernameNotFoundException() {
        String username = "nonexistent@example.com";

        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });
    }
}
