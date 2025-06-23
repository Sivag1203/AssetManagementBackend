package com.backend.assetmanagement.controller;

import com.backend.assetmanagement.dto.EmployeeDTO;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.model.Employee;
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

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "http://localhost:5173/")
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
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDTO getById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/update/{id}")
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable int id) {
        return employeeService.deleteEmployee(id);
    }

    @PostMapping("/register")
    public ResponseEntity<Employee> register(@RequestBody Employee dto) {
        String encodedPassword = passwordEncoder.encode(dto.getAuth().getPassword());
        dto.getAuth().setPassword(encodedPassword);
        Employee saved = employeeService.addEmployee(dto);
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
    
    @GetMapping("/details")
    public ResponseEntity<Map<String, Object>> getLoggedInEmployeeDetails(Principal principal) {
        String email = principal.getName();
        Employee employee = employeeService.getEmployeeByEmail(email);

        Map<String, Object> response = new HashMap<>();
        response.put("id", employee.getId());
        response.put("name", employee.getName());
        response.put("email", employee.getEmail());
        response.put("phone", employee.getPhone());
        response.put("department", employee.getDepartment());
        response.put("address", employee.getAddress());
        response.put("role", employee.getAuth().getRole());
        return ResponseEntity.ok(response);
    }
}
