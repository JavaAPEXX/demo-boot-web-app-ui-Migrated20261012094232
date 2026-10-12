package com.demo.app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demo.app.model.Document;
import com.demo.app.service.UserService;

@ExtendWith(MockitoExtension.class)
public class DocRepositoryTest {

    @InjectMocks
    private DocRepository docRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given a valid user ID, when finding user documents, then return a list of documents")
    public void givenValidUserId_whenFindUserDocs_thenReturnUserDocs() {
        // Arrange
        long userId = 1L;
        Document doc1 = new Document();
        Document doc2 = new Document();
        List<Document> expectedDocs = List.of(doc1, doc2);
        when(userService.getUser(userId)).thenReturn(doc1);
        when(userService.getUser(userId)).thenReturn(doc2);
        when(docRepository.findUserDocs(userId)).thenReturn(expectedDocs);

        // Act
        List<Document> result = docRepository.findUserDocs(userId);

        // Assert
        assertEquals(expectedDocs, result);
    }

    @Test
    @DisplayName("Given a non-existing user ID, when finding user documents, then throw UserNotFoundException")
    public void givenNonExistingUserId_whenFindUserDocs_thenThrowUserNotFoundException() {
        // Arrange
        long userId = 100L;
        when(userService.getUser(userId)).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> docRepository.findUserDocs(userId));
    }

    @Test
    @DisplayName("Given a null user ID, when finding user documents, then throw IllegalArgumentException")
    public void givenNullUserId_whenFindUserDocs_thenThrowIllegalArgumentException() {
        // Arrange
        Long userId = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> docRepository.findUserDocs(userId));
    }

    @Test
    @DisplayName("Given an empty user ID, when finding user documents, then throw IllegalArgumentException")
    public void givenEmptyUserId_whenFindUserDocs_thenThrowIllegalArgumentException() {
        // Arrange
        String userId = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> docRepository.findUserDocs(userId));
    }

    @Test
    @DisplayName("Given a valid user ID with multiple documents, when finding user documents, then return all documents")
    public void givenValidUserIdWithMultipleDocuments_whenFindUserDocs_thenReturnAllDocuments() {
        // Arrange
        long userId = 1L;
        Document doc1 = new Document();
        Document doc2 = new Document();
        List<Document> expectedDocs = List.of(doc1, doc2);
        when(userService.getUser(userId)).thenReturn(doc1);
        when(userService.getUser(userId)).thenReturn(doc2);
        when(docRepository.findUserDocs(userId)).thenReturn(expectedDocs);

        // Act
        List<Document> result = docRepository.findUserDocs(userId);

        // Assert
        assertEquals(expectedDocs, result);
    }

    @Test
    @DisplayName("Given a valid user ID with a single document, when finding user documents, then return the single document")
    public void givenValidUserIdWithSingleDocument_whenFindUserDocs_thenReturnSingleDocument() {
        // Arrange
        long userId = 1L;
        Document doc1 = new Document();
        List<Document> expectedDocs = List.of(doc1);
        when(userService.getUser(userId)).thenReturn(doc1);