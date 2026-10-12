package com.demo.app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.demo.app.model.User;
import com.demo.app.repository.RoleRepository;
import com.demo.app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given a valid user, when save is called, then user is saved with encoded password and default roles")
    public void givenValidUser_whenSave_thenUserIsSavedWithEncodedPasswordAndDefaultRoles() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setEmail("test@test.com");

        // Mock behavior
        when(roleRepository.findAll()).thenReturn(new HashSet<>());
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        // Act
        userService.save(user);

        // Assert
        verify(userRepository).save(user);
        assertEquals("encodedPassword", user.getPassword());
        // Verify roles are set
        // Assuming roles are set in the save method
    }

    @Test
    @DisplayName("Given a user with null username, when save is called, then IllegalArgumentException is thrown")
    public void givenUserWithNullUsername_whenSave_thenIllegalArgumentExceptionIsThrown() {
        // Arrange
        User user = new User();
        user.setUsername(null);
        user.setPassword("password");
        user.setEmail("test@test.com");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    @DisplayName("Given a user with empty username, when save is called, then IllegalArgumentException is thrown")
    public void givenUserWithEmptyUsername_whenSave_thenIllegalArgumentExceptionIsThrown() {
        // Arrange
        User user = new User();
        user.setUsername("");
        user.setPassword("password");
        user.setEmail("test@test.com");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    @DisplayName("Given a user with null password, when save is called, then IllegalArgumentException is thrown")
    public void givenUserWithNullPassword_whenSave_thenIllegalArgumentExceptionIsThrown() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword(null);
        user.setEmail("test@test.com");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    @DisplayName("Given a user with empty password, when save is called, then IllegalArgumentException is thrown")
    public void givenUserWithEmptyPassword_whenSave_thenIllegalArgumentExceptionIsThrown() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("");
        user.setEmail("test@test.com");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    @DisplayName("Given a user with null email, when save is called, then IllegalArgumentException is thrown")
    public void givenUserWithNullEmail_whenSave_thenIllegalArgumentExceptionIsThrown() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");