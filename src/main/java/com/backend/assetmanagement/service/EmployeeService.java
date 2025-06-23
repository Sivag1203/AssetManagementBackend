package com.backend.assetmanagement.service;

import com.backend.assetmanagement.dto.EmployeeDTO;
import com.backend.assetmanagement.enums.Level;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.model.Employee;
import com.backend.assetmanagement.repository.AuthRepository;
import com.backend.assetmanagement.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public EmployeeService(EmployeeRepository employeeRepository, AuthRepository authRepository) {
        this.employeeRepository = employeeRepository;
        this.authRepository = authRepository;
    }

    public Employee addEmployee(Employee employee) {
        Auth auth = employee.getAuth();
        auth.setPassword(passwordEncoder.encode(auth.getPassword()));
        Auth savedAuth = authRepository.save(auth);
        employee.setAuth(savedAuth);
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    public EmployeeDTO getEmployeeById(int id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(emp.getId());
        dto.setName(emp.getName());
        dto.setEmail(emp.getEmail());
        dto.setPhone(emp.getPhone());
        dto.setDepartment(emp.getDepartment());
        dto.setAddress(emp.getAddress());
        dto.setLevel(emp.getLevel());
        dto.setAuth(emp.getAuth());
        return dto;
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) {
        if (updatedEmployee == null) {
            throw new IllegalArgumentException("Updated employee details cannot be null");
        }

        if (updatedEmployee.getName() == null || updatedEmployee.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        // Update basic fields
        existing.setName(updatedEmployee.getName());
        existing.setEmail(updatedEmployee.getEmail());
        existing.setPhone(updatedEmployee.getPhone());
        existing.setDepartment(updatedEmployee.getDepartment());
        existing.setAddress(updatedEmployee.getAddress());

        Auth updatedAuth = updatedEmployee.getAuth();
        if (updatedAuth != null) {
            Auth existingAuth = existing.getAuth();

            existingAuth.setEmail(updatedAuth.getEmail());

            String incomingPassword = updatedAuth.getPassword();
            boolean isEncoded = incomingPassword != null && incomingPassword.startsWith("$2");

            if (!isEncoded && incomingPassword != null && !incomingPassword.isEmpty()) {
                // New raw password -> encode and save
                existingAuth.setPassword(passwordEncoder.encode(incomingPassword));
            } else {
                // Already encoded or unchanged -> keep existing password
                existingAuth.setPassword(existingAuth.getPassword());
            }

            existingAuth.setRole(updatedAuth.getRole());
            authRepository.save(existingAuth);
        }

        return employeeRepository.save(existing);
    }

    public String deleteEmployee(int id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(emp);
        return "Employee with id " + id + " deleted successfully.";
    }
    
    public Employee getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByAuthEmail(email);
        if (employee == null) {
            throw new RuntimeException("Employee not found with email: " + email);
        }
        return employee;
    }
}
