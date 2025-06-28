package com.backend.assetmanagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.backend.assetmanagement.enums.Level;
import com.backend.assetmanagement.model.Admin;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.model.Employee;
import com.backend.assetmanagement.repository.AuthRepository;
import com.backend.assetmanagement.security.CustomUserDetailsService;
import com.backend.assetmanagement.security.JwtUtil;
import com.backend.assetmanagement.service.AdminService;
import com.backend.assetmanagement.service.AuthService;
import com.backend.assetmanagement.service.EmployeeService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private AdminService adminService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/add")
    public Auth addAuth(@RequestBody Auth auth) {
        return authService.addAuth(auth);
    }

    @GetMapping("/all")
    public List<Auth> getAllAuths() {
        return authService.getAllAuths();
    }

    @GetMapping("/{id}")
    public Auth getAuthById(@PathVariable int id) {
        return authService.getAuthById(id);
    }

    @PutMapping("/update/{id}")
    public Auth updateAuth(@PathVariable int id, @RequestBody Auth updatedAuth) {
        return authService.updateAuth(id, updatedAuth);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAuth(@PathVariable int id) {
        authService.deleteAuth(id);
        return "Auth deleted successfully with id: " + id;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> payload) {
        try {
            String role = (String) payload.get("role");
            Map<String, Object> userData = (Map<String, Object>) payload.get("user");

            // Validate required fields
            if (role == null || userData == null || userData.get("auth") == null) {
                return ResponseEntity.badRequest().body("Missing required registration details");
            }

            Map<String, String> authData = (Map<String, String>) userData.get("auth");
            String email = authData.get("email");
            String rawPassword = authData.get("password");

            if (email == null || rawPassword == null) {
                return ResponseEntity.badRequest().body("Email or password cannot be null");
            }

            Auth auth = new Auth();
            auth.setEmail(email);
            auth.setPassword(passwordEncoder.encode(rawPassword));
            auth.setRole(role.toLowerCase());

            if (role.equalsIgnoreCase("admin")) {
                Admin admin = new Admin();
                admin.setName((String) userData.get("name"));
                admin.setEmail(email);
                admin.setPhone((String) userData.get("phone"));
                admin.setDepartment((String) userData.get("department"));
                admin.setAddress((String) userData.get("address"));
                admin.setAuth(auth);
                Admin saved = adminService.addAdmin(admin);
                return ResponseEntity.ok(saved);
            } else if (role.equalsIgnoreCase("employee")) {
                Employee employee = new Employee();
                employee.setName((String) userData.get("name"));
                employee.setEmail(email);
                employee.setPhone((String) userData.get("phone"));
                employee.setDepartment((String) userData.get("department"));
                employee.setAddress((String) userData.get("address"));
                String levelStr = (String) userData.get("level");
                employee.setLevel(Level.valueOf(levelStr.toUpperCase()));
                employee.setAuth(auth);
                Employee saved = employeeService.addEmployee(employee);
                return ResponseEntity.ok(saved);
            } else {
                return ResponseEntity.badRequest().body("Invalid role. Use 'admin' or 'employee'.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Auth loginRequest) {
        try {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());

            if (passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
                Auth auth = authRepository.findByEmail(loginRequest.getEmail());
                String token = jwtUtil.generateToken(auth.getEmail(), auth.getRole());
                return ResponseEntity.ok(Map.of("token", token, "role", auth.getRole()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }

}