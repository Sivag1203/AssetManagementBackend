package com.backend.assetmanagement.controller;

import com.backend.assetmanagement.dto.AdminDTO;
import com.backend.assetmanagement.model.Admin;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.repository.AuthRepository;
import com.backend.assetmanagement.security.CustomUserDetailsService;
import com.backend.assetmanagement.security.JwtUtil;
import com.backend.assetmanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173/")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/add")
    public Admin addAdmin(@RequestBody Admin admin) {
        return adminService.addAdmin(admin);
    }

    @GetMapping("/all")
    public List<AdminDTO> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public AdminDTO getAdminById(@PathVariable int id) {
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
                Auth auth = authRepository.findByEmail(loginRequest.getEmail());
                String token = jwtUtil.generateToken(auth.getEmail(), auth.getRole());
                return ResponseEntity.ok(Map.of("token", token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }
    
    @GetMapping("/details")
    public ResponseEntity<Map<String, Object>> getLoggedInAdminDetails(Principal principal) {
        String email = principal.getName();
        Admin admin = adminService.getAdminByEmail(email);

        Map<String, Object> response = new HashMap<>();
        response.put("id", admin.getId());
        response.put("name", admin.getName());
        response.put("email", admin.getEmail());
        response.put("phone", admin.getPhone());
        response.put("department", admin.getDepartment());
        response.put("address", admin.getAddress());
        response.put("role", admin.getAuth().getRole());
        return ResponseEntity.ok(response);
    }

}
