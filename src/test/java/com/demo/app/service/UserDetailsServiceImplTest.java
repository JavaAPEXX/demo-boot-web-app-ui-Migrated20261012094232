package com.demo.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.demo.app.model.Role;
import com.demo.app.model.User;
import com.demo.app.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given existing user when loadUserByUsername is called then returns UserDetails")
    public void givenExistingUser_whenLoadUserByUsername_thenReturnsUserDetails() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRoles(new HashSet<>());
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        // Act
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("testUser");

        // Assert
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    @DisplayName("Given non-existing user when loadUserByUsername is called then returns null")
    public void givenNonExistingUser_whenLoadUserByUsername_thenReturnsNull() {
        // Arrange
        when(userRepository.findByUsername("nonExistingUser")).thenReturn(null);

        // Act
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("nonExistingUser");

        // Assert
        assertNull(userDetails);
    }

    @Test
    @DisplayName("Given null username when loadUserByUsername is called then throws UsernameNotFoundException")
    public void givenNullUsername_whenLoadUserByUsername_thenThrowsUsernameNotFoundException() {
        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userDetailsServiceImpl.loadUserByUsername(null));
    }

    @Test
    @DisplayName("Given empty username when loadUserByUsername is called then throws UsernameNotFoundException")
    public void givenEmptyUsername_whenLoadUserByUsername_thenThrowsUsernameNotFoundException() {
        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userDetailsServiceImpl.loadUserByUsername(""));
    }

    @Test
    @DisplayName("Given user with no roles when loadUserByUsername is called then returns UserDetails without authorities")
    public void givenUserWithNoRoles_whenLoadUserByUsername_thenReturnsUserDetailsWithoutAuthorities() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRoles(new HashSet<>());
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        // Act
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("testUser");

        // Assert
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
        assertEquals(0, userDetails.getAuthorities().size());
    }

    @Test
    @DisplayName("Given user with multiple roles when loadUserByUsername is called then returns UserDetails with all authorities")
    public void givenUserWithMultipleRoles_whenLoadUserByUsername_thenReturnsUserDetailsWithAll