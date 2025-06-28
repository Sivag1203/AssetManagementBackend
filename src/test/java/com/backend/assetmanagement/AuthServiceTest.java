package com.backend.assetmanagement;

import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.repository.AuthRepository;
import com.backend.assetmanagement.service.AuthService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks private AuthService authService;

    @Mock private AuthRepository authRepository;
    @Mock private PasswordEncoder passwordEncoder;

    private AutoCloseable mocks;
    private Auth auth;

    @BeforeEach
    void init() {
        mocks = MockitoAnnotations.openMocks(this);

        auth = new Auth();
        auth.setId(1);
        auth.setEmail("test@example.com");
        auth.setPassword("raw");
        auth.setRole("employee");
    }

    @AfterEach
    void close() throws Exception {
        mocks.close();
        auth = null;
    }

    @Test
    void addAuth_ok() {
        when(passwordEncoder.encode("raw")).thenReturn("enc");
        when(authRepository.save(any(Auth.class))).thenAnswer(i -> i.getArgument(0));

        Auth saved = authService.addAuth(auth);

        assertEquals("enc", saved.getPassword());
        verify(authRepository).save(auth);
    }

    @Test
    void getAll() {
        when(authRepository.findAll()).thenReturn(Arrays.asList(auth));
        List<Auth> list = authService.getAllAuths();
        assertEquals(1, list.size());
    }

    @Test
    void getById_ok() {
        when(authRepository.findById(1)).thenReturn(Optional.of(auth));
        Auth found = authService.getAuthById(1);
        assertEquals("test@example.com", found.getEmail());
    }

    @Test
    void getById_notFound() {
        when(authRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> authService.getAuthById(2));
    }

    @Test
    void update_ok() {
        when(authRepository.findById(1)).thenReturn(Optional.of(auth));
        when(passwordEncoder.encode("new")).thenReturn("newEnc");
        when(authRepository.save(any(Auth.class))).thenAnswer(i -> i.getArgument(0));

        Auth upd = new Auth();
        upd.setEmail("u@test.com");
        upd.setPassword("new");
        upd.setRole("admin");

        Auth result = authService.updateAuth(1, upd);

        assertEquals("u@test.com", result.getEmail());
        assertEquals("newEnc",    result.getPassword());
        assertEquals("admin",     result.getRole());
    }

    @Test
    void delete_ok() {
        when(authRepository.findById(1)).thenReturn(Optional.of(auth));
        authService.deleteAuth(1);
        verify(authRepository).delete(auth);
    }
}
