package com.backend.assetmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import com.backend.assetmanagement.dto.AdminDTO;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Admin;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.repository.AdminRepository;
import com.backend.assetmanagement.repository.AuthRepository;
import com.backend.assetmanagement.service.AdminService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AuthRepository authRepository;

    private Admin admin;
    private Auth auth;

    @BeforeEach
    public void setUp() {
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

        Admin result = adminService.addAdmin(admin);

        assertEquals(admin, result);
    }

    @Test
    public void testGetAdminById_Success() {
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        AdminDTO result = adminService.getAdminById(1);

        assertEquals(admin.getName(), result.getName());
        assertEquals(admin.getEmail(), result.getEmail());
    }

    @Test
    public void testGetAdminById_NotFound() {
        when(adminRepository.findById(100)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            adminService.getAdminById(100);
        });

        assertEquals("Admin not found with id: 100".toLowerCase(), exception.getMessage().toLowerCase());
    }

    @Test
    public void testDeleteAdmin_Success() {
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        String message = adminService.deleteAdmin(1);

        verify(adminRepository).delete(admin);

        assertEquals("Admin with id 1 deleted successfully.", message);
    }

    @Test
    public void testDeleteAdmin_NotFound() {
        when(adminRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            adminService.deleteAdmin(99);
        });

        assertEquals("Admin not found with id: 99".toLowerCase(), exception.getMessage().toLowerCase());
    }

    @AfterEach
    public void tearDown() {
        admin = null;
        auth = null;
    }
}
