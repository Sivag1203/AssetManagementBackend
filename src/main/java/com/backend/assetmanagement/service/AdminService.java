package com.backend.assetmanagement.service;

import com.backend.assetmanagement.dto.AdminDTO;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Admin;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.repository.AdminRepository;
import com.backend.assetmanagement.repository.AuthRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final AuthRepository authRepository;

    public AdminService(AdminRepository adminRepository, AuthRepository authRepository) {
        this.adminRepository = adminRepository;
        this.authRepository = authRepository;
    }

    public Admin addAdmin(Admin admin) {
        Auth auth = admin.getAuth();
        Auth savedAuth = authRepository.save(auth);
        admin.setAuth(savedAuth);
        return adminRepository.save(admin);
    }

    public List<AdminDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return AdminDTO.fromEntityList(admins);
    }

    public AdminDTO getAdminById(int id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));
        return AdminDTO.fromEntity(admin);
    }

    public Admin updateAdmin(int id, Admin updatedAdmin) {
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));

        existingAdmin.setName(updatedAdmin.getName());
        existingAdmin.setEmail(updatedAdmin.getEmail());
        existingAdmin.setPhone(updatedAdmin.getPhone());
        existingAdmin.setDepartment(updatedAdmin.getDepartment());
        existingAdmin.setAddress(updatedAdmin.getAddress());

        Auth updatedAuth = updatedAdmin.getAuth();
        Auth existingAuth = existingAdmin.getAuth();

        existingAuth.setEmail(updatedAuth.getEmail());
        existingAuth.setPassword(updatedAuth.getPassword());
        existingAuth.setRole(updatedAuth.getRole());

        authRepository.save(existingAuth);
        return adminRepository.save(existingAdmin);
    }

    public String deleteAdmin(int id) {
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));
        adminRepository.delete(existingAdmin);
        return "Admin with id " + id + " deleted successfully.";
    }
}
