package com.backend.assetmanagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Admin;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.repository.AdminRepository;
import com.backend.assetmanagement.repository.AuthRepository;
import com.backend.assetmanagement.service.AdminService;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AuthRepository authRepository;

    private Admin admin;
    private Auth auth;
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        auth = new Auth();
        auth.setId(1);
        auth.setEmail("admin@test.com");
        auth.setPassword("password");
        auth.setRole("ADMIN");

        admin = new Admin();
        admin.setId(1);
        admin.setName("Admin User");
        admin.setEmail("admin@test.com");
        admin.setPhone("1234567890");
        admin.setDepartment("IT");
        admin.setAddress("India");
        admin.setAuth(auth);
    }

    @Test
    public void testAddAdmin_Success() {
        when(authRepository.save(auth)).thenReturn(auth);
        when(adminRepository.save(admin)).thenReturn(admin);

        Admin saved = adminService.addAdmin(admin);

        assertNotNull(saved);
        assertEquals("Admin User", saved.getName());
        verify(authRepository, times(1)).save(auth);
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    public void testGetAdminById_Success() {
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        Admin found = adminService.getAdminById(1);

        assertNotNull(found);
        assertEquals("admin@test.com", found.getEmail());
    }

    @Test
    public void testGetAdminById_NotFound() {
        when(adminRepository.findById(99)).thenReturn(Optional.empty());

        Exception ex = assertThrows(ResourceNotFoundException.class, () -> {
            adminService.getAdminById(99);
        });

        assertTrue(ex.getMessage().toLowerCase().contains("admin not found with id"));
    }

    @Test
    public void testDeleteAdmin_Success() {
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        String result = adminService.deleteAdmin(1);

        assertEquals("Admin with id 1 deleted successfully.", result);
        verify(adminRepository, times(1)).delete(admin);
    }

    @Test
    public void testDeleteAdmin_NotFound() {
        when(adminRepository.findById(88)).thenReturn(Optional.empty());

        Exception ex = assertThrows(ResourceNotFoundException.class, () -> {
            adminService.deleteAdmin(88);
        });

        assertTrue(ex.getMessage().toLowerCase().contains("admin not found with id"));
    }

    @AfterEach
    public void tearDown() throws Exception {
        admin = null;
        auth = null;
        closeable.close();
    }
}
