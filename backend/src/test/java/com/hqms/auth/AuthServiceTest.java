package com.hqms.auth;

import com.hqms.auth.dto.AuthResponse;
import com.hqms.auth.dto.LoginRequest;
import com.hqms.auth.dto.RegisterRequest;
import com.hqms.auth.service.AuthService;
import com.hqms.auth.service.JwtService;
import com.hqms.user.entity.Role;
import com.hqms.user.entity.User;
import com.hqms.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtService jwtService;
    @InjectMocks
    AuthService authService;

    @Test
    void register_newEmail_savesPatientWithHashedPasswordAndReturnsToken() {
        RegisterRequest request = new RegisterRequest("Asha", "Asha@Mail.com", "9876543210", "password123");
        when(userRepository.existsByEmail("asha@mail.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("HASH");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        when(jwtService.generateToken(any(), eq("asha@mail.com"), eq(Role.PATIENT))).thenReturn("TOKEN");

        AuthResponse response = authService.register(request);

        assertEquals("TOKEN", response.token());
        assertEquals("PATIENT", response.role());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals("HASH", captor.getValue().getPasswordHash());
        assertEquals(Role.PATIENT, captor.getValue().getRole());
    }

    @Test
    void register_duplicateEmail_throwsConflictAndSavesNothing() {
        RegisterRequest request = new RegisterRequest("Asha", "asha@mail.com", null, "password123");
        when(userRepository.existsByEmail("asha@mail.com")).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> authService.register(request));

        assertEquals(409, ex.getStatusCode().value());
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_correctPassword_returnsToken() {
        User user = existingUser();
        when(userRepository.findByEmail("asha@mail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "HASH")).thenReturn(true);
        when(jwtService.generateToken(any(), eq("asha@mail.com"), eq(Role.PATIENT))).thenReturn("TOKEN");

        AuthResponse response = authService.login(new LoginRequest("asha@mail.com", "password123"));

        assertEquals("TOKEN", response.token());
    }

    @Test
    void login_wrongPassword_throwsUnauthorized() {
        when(userRepository.findByEmail("asha@mail.com")).thenReturn(Optional.of(existingUser()));
        when(passwordEncoder.matches("bad", "HASH")).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> authService.login(new LoginRequest("asha@mail.com", "bad")));

        assertEquals(401, ex.getStatusCode().value());
    }

    @Test
    void login_unknownEmail_throwsUnauthorized() {
        when(userRepository.findByEmail("nobody@mail.com")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> authService.login(new LoginRequest("nobody@mail.com", "x")));

        assertEquals(401, ex.getStatusCode().value());
    }

    private User existingUser() {
        User user = new User();
        user.setFullName("Asha");
        user.setEmail("asha@mail.com");
        user.setPasswordHash("HASH");
        user.setRole(Role.PATIENT);
        return user;
    }
}
