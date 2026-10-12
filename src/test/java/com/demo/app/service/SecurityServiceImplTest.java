package com.demo.app.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceImplTest {

    @InjectMocks
    private SecurityServiceImpl securityService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("findLoggedInUsername should return null when no user is logged in")
    public void findLoggedInUsername_noLoggedInUser() {
        // Arrange
        SecurityContextHolder.getContext().setAuthentication(null);

        // Act
        String result = securityService.findLoggedInUsername();

        // Assert
        assertNull(result, "findLoggedInUsername should return null when no user is logged in");
    }

    @Test
    @DisplayName("findLoggedInUsername should return the username of the logged in user")
    public void findLoggedInUsername_loggedInUser() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "password"));

        // Act
        String result = securityService.findLoggedInUsername();

        // Assert
        assertEquals("testUser", result, "findLoggedInUsername should return the username of the logged in user");
    }

    @Test
    @DisplayName("autologin should authenticate and set the authentication context when valid credentials are provided")
    public void autologin_validCredentials() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userDetails.getPassword()).thenReturn("password");
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(userDetails);

        // Act
        securityService.autologin("testUser", "password");

        // Assert
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication, "Authentication should be set in the context");
        assertEquals(userDetails, authentication.getPrincipal(), "Principal should be the loaded user details");
        assertTrue(authentication.isAuthenticated(), "Authentication should be authenticated");
        logger.debug("Auto login test passed!");
    }

    @Test
    @DisplayName("autologin should not authenticate and set the authentication context when invalid credentials are provided")
    public void autologin_invalidCredentials() {
        // Arrange
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(null);

        // Act
        securityService.autologin("testUser", "password");

        // Assert
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication, "Authentication should not be set in the context");
        logger.debug("Auto login test failed!");
    }

    @Test
    @DisplayName("autologin should handle UsernameNotFoundException when user does not exist")
    public void autologin_userNotFoundException() {
        // Arrange
        when(userDetailsService.loadUserByUsername("testUser")).thenThrow(new UsernameNotFoundException("User not found"));

        // Act
        securityService.autologin("testUser", "password");

        // Assert
        logger.debug