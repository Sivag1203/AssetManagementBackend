package com.backend.assetmanagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.repository.AuthRepository;
import com.backend.assetmanagement.service.AuthService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthRepository authRepository;

    private AutoCloseable closeable;

    private Auth sampleAuth;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        sampleAuth = new Auth();
        sampleAuth.setId(1);
        sampleAuth.setEmail("test@example.com");
        sampleAuth.setPassword("pass123");
        sampleAuth.setRole("employee");
    }

    @Test
    public void testAddAuth() {
        when(authRepository.save(sampleAuth)).thenReturn(sampleAuth);

        Auth result = authService.addAuth(sampleAuth);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    public void testGetAllAuths() {
        Auth auth2 = new Auth();
        auth2.setId(2);
        auth2.setEmail("second@example.com");
        auth2.setPassword("secondpass");
        auth2.setRole("admin");

        when(authRepository.findAll()).thenReturn(Arrays.asList(sampleAuth, auth2));

        List<Auth> authList = authService.getAllAuths();
        assertEquals(2, authList.size());
    }

    @Test
    public void testGetAuthById_WhenExists() {
        when(authRepository.findById(1)).thenReturn(Optional.of(sampleAuth));

        Auth result = authService.getAuthById(1);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    public void testGetAuthById_WhenNotExists() {
        when(authRepository.findById(10)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            authService.getAuthById(10);
        });
    }

    @Test
    public void testUpdateAuth() {
        when(authRepository.findById(1)).thenReturn(Optional.of(sampleAuth));

        Auth updated = new Auth();
        updated.setEmail("updated@example.com");
        updated.setPassword("newpass");
        updated.setRole("admin");

        when(authRepository.save(any(Auth.class))).thenReturn(updated);

        Auth result = authService.updateAuth(1, updated);
        assertEquals("updated@example.com", result.getEmail());
        assertEquals("admin", result.getRole());
    }

    @Test
    public void testDeleteAuth() {
        when(authRepository.findById(1)).thenReturn(Optional.of(sampleAuth));

        authService.deleteAuth(1);
        verify(authRepository, times(1)).delete(sampleAuth);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
        sampleAuth = null;
    }
}
