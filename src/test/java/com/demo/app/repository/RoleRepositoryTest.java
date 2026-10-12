package com.demo.app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demo.app.model.Role;
import com.demo.app.model.RoleRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RoleRepositoryTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleRepository target;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test method to cover the public business method save(Role role)
    @Test
    @DisplayName("Given a valid Role, when save is called, then the Role is saved to the database")
    public void givenValidRole_whenSave_thenRoleIsSaved() {
        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName("Admin");

        // Act
        target.save(role);

        // Assert
        verify(roleRepository, times(1)).save(role);
    }

    // Test method to cover the public business method findById(Long id)
    @Test
    @DisplayName("Given a valid Role ID, when findById is called, then the Role is found in the database")
    public void givenValidRoleId_whenFindById_thenRoleIsFound() {
        // Arrange
        Long id = 1L;
        Role role = new Role();
        role.setId(id);
        role.setName("Admin");

        when(roleRepository.findById(id)).thenReturn(Optional.of(role));

        // Act
        Optional<Role> result = target.findById(id);

        // Assert
        assertEquals(Optional.of(role), result);
        verify(roleRepository, times(1)).findById(id);
    }

    // Test method to cover the public business method findById(Long id) with no matching role
    @Test
    @DisplayName("Given a non-existing Role ID, when findById is called, then an empty Optional is returned")
    public void givenNonExistingRoleId_whenFindById_thenEmptyOptional() {
        // Arrange
        Long id = 1L;
        when(roleRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Role> result = target.findById(id);

        // Assert
        assertEquals(Optional.empty(), result);
        verify(roleRepository, times(1)).findById(id);
    }

    // Test method to cover the public business method deleteById(Long id)
    @Test
    @DisplayName("Given a valid Role ID, when deleteById is called, then the Role is deleted from the database")
    public void givenValidRoleId_whenDeleteById_thenRoleIsDeleted() {
        // Arrange
        Long id = 1L;

        // Act
        target.deleteById(id);

        // Assert
        verify(roleRepository, times(1)).deleteById(id);
    }

    // Test method to cover the public business method findAll()
    @Test
    @DisplayName("Given no roles in the database, when findAll is called, then an empty list is returned")
    public void givenNoRoles_whenFindAll_thenEmptyList() {
        // Act
        Iterable<Role> result = target.findAll();

        // Assert
        assertEquals(0, result.spliterator().estimateSize());
        verify(roleRepository, times(1)).findAll();
    }

    // Test method to cover the public business method findAll() with multiple roles
    @Test
    @DisplayName("Given multiple roles in the database, when findAll is called, then all roles are returned")
    public void givenMultipleRoles_whenFindAll_thenAllRolesReturned() {
        // Arrange
        Role role1 = new Role();
        role