package com.backend.assetmanagement.service;

import com.backend.assetmanagement.dto.AdminDTO;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Admin;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.repository.AdminRepository;
import com.backend.assetmanagement.repository.AuthRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final AuthRepository authRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, AuthRepository authRepository) {
        this.adminRepository = adminRepository;
        this.authRepository = authRepository;
    }

    public Admin addAdmin(Admin admin) {
        if (admin == null || admin.getAuth() == null) {
            throw new IllegalArgumentException("Admin or Auth details cannot be null");
        }

        if (admin.getName() == null || admin.getName().isEmpty()) {
            throw new IllegalArgumentException("Admin name is required");
        }

        if (admin.getEmail() == null || admin.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Admin email is required");
        }

        if (admin.getAuth().getEmail() == null || admin.getAuth().getEmail().isEmpty()) {
            throw new IllegalArgumentException("Auth email is required");
        }

        if (admin.getAuth().getPassword() == null || admin.getAuth().getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        Auth savedAuth = authRepository.save(admin.getAuth());
        admin.setAuth(savedAuth);
        return adminRepository.save(admin);
    }

    public List<AdminDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return AdminDTO.fromEntityList(admins);
    }

    public Admin getAdminById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid admin ID");
        }

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));
        return admin;
    }

    public Admin updateAdmin(int id, Admin updatedAdmin) {
        if (updatedAdmin == null) {
            throw new IllegalArgumentException("Updated admin details cannot be null");
        }

        if (updatedAdmin.getName() == null || updatedAdmin.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));

        existingAdmin.setName(updatedAdmin.getName());
        existingAdmin.setEmail(updatedAdmin.getEmail());
        existingAdmin.setPhone(updatedAdmin.getPhone());
        existingAdmin.setDepartment(updatedAdmin.getDepartment());
        existingAdmin.setAddress(updatedAdmin.getAddress());

        Auth updatedAuth = updatedAdmin.getAuth();
        if (updatedAuth != null) {
            Auth existingAuth = existingAdmin.getAuth();

            existingAuth.setEmail(updatedAuth.getEmail());

            String incomingPassword = updatedAuth.getPassword();

            // Check if incoming password is already encoded (bcrypt starts with $2)
            boolean isEncoded = incomingPassword != null && incomingPassword.startsWith("$2");

            if (!isEncoded && !incomingPassword.isEmpty()) {
                // Only encode and update if it's a new raw password
                existingAuth.setPassword(passwordEncoder.encode(incomingPassword));
            } else {
                // Keep old password if the field is encoded (assume no change)
                existingAuth.setPassword(existingAuth.getPassword());
            }

            existingAuth.setRole(updatedAuth.getRole());
            authRepository.save(existingAuth);
        }

        return adminRepository.save(existingAdmin);
    }
    public String deleteAdmin(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid admin ID");
        }

        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));
        adminRepository.delete(existingAdmin);
        return "Admin with id " + id + " deleted successfully.";
    }
    
    public Admin getAdminByEmail(String email) {
        Admin admin = adminRepository.findByAuthEmail(email);
        if (admin == null) {
            throw new RuntimeException("Admin not found with email: " + email);
        }
        return admin;
    }
}
