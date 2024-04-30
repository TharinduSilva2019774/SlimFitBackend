package com.example.slimfitbackend.service;

import com.example.slimfitbackend.model.User;
import com.example.slimfitbackend.payload.AuthenticationRequest;
import com.example.slimfitbackend.payload.AuthenticationResponse;
import com.example.slimfitbackend.payload.RegisterRequest;
import com.example.slimfitbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegister() {
        RegisterRequest request = new RegisterRequest("test@example.com", "John", "Doe", "password");
        User user = new User(request.getEmail(), request.getFirstname(), request.getLastname(), "encoded_password");

        when(passwordEncoder.encode("password")).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwt_token");

        AuthenticationResponse response = authenticationService.register(request);

        assertEquals("jwt_token", response.getToken());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testAuthenticate() {
        AuthenticationRequest request = new AuthenticationRequest("test@example.com", "password");
        User user = new User("test@example.com", "John", "Doe", "encoded_password");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwt_token");

        AuthenticationResponse response = authenticationService.authenticate(request);

        assertEquals("jwt_token", response.getToken());
        verify(authenticationManager, times(1)).authenticate(any());
    }
}
