package com.backend.assetmanagement.controller;

import com.backend.assetmanagement.model.Admin;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.security.CustomUserDetailsService;
import com.backend.assetmanagement.security.JwtUtil;
import com.backend.assetmanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/add")
    public Admin addAdmin(@RequestBody Admin admin) {
        return adminService.addAdmin(admin);
    }

    @GetMapping("/all")
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable int id) {
        return adminService.getAdminById(id);
    }

    @PutMapping("/update/{id}")
    public Admin updateAdmin(@PathVariable int id, @RequestBody Admin admin) {
        return adminService.updateAdmin(id, admin);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable int id) {
        return adminService.deleteAdmin(id);
    }
    
    @PostMapping("/register")
    public ResponseEntity<Admin> register(@RequestBody Admin admin) {
        String encodedPassword = passwordEncoder.encode(admin.getAuth().getPassword());
        admin.getAuth().setPassword(encodedPassword);
        Admin savedAdmin = adminService.addAdmin(admin);
        return ResponseEntity.ok(savedAdmin);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Auth loginRequest) {
        try {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());

            if (passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
                String token = jwtUtil.generateToken(userDetails.getUsername());
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }
}