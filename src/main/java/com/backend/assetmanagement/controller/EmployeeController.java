package com.backend.assetmanagement.controller;

import com.backend.assetmanagement.dto.EmployeeDTO;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.repository.AuthRepository;
import com.backend.assetmanagement.security.CustomUserDetailsService;
import com.backend.assetmanagement.security.JwtUtil;
import com.backend.assetmanagement.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/add")
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO dto) {
        return employeeService.addEmployee(dto);
    }

    @GetMapping("/all")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDTO getById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/update/{id}")
    public EmployeeDTO updateEmployee(@PathVariable int id, @RequestBody EmployeeDTO dto) {
        return employeeService.updateEmployee(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable int id) {
        return employeeService.deleteEmployee(id);
    }

    @PostMapping("/register")
    public ResponseEntity<EmployeeDTO> register(@RequestBody EmployeeDTO dto) {
        String encodedPassword = passwordEncoder.encode(dto.getAuth().getPassword());
        dto.getAuth().setPassword(encodedPassword);
        EmployeeDTO saved = employeeService.addEmployee(dto);
        return ResponseEntity.ok(saved);
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
}
