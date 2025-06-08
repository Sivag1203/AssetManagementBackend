package com.backend.assetmanagement.service;

import com.backend.assetmanagement.dto.EmployeeDTO;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.model.Employee;
import com.backend.assetmanagement.repository.AuthRepository;
import com.backend.assetmanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;

    public EmployeeService(EmployeeRepository employeeRepository, AuthRepository authRepository) {
        this.employeeRepository = employeeRepository;
        this.authRepository = authRepository;
    }

    public EmployeeDTO addEmployee(EmployeeDTO dto) {
        Auth savedAuth = authRepository.save(dto.getAuth());

        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setAddress(dto.getAddress());
        employee.setDepartment(dto.getDepartment());
        employee.setLevel(dto.getLevel());
        employee.setAuth(savedAuth);

        Employee saved = employeeRepository.save(employee);
        dto.setId(saved.getId());
        return dto;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(emp -> {
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
        }).collect(Collectors.toList());
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

    public EmployeeDTO updateEmployee(int id, EmployeeDTO dto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setDepartment(dto.getDepartment());
        existing.setAddress(dto.getAddress());
        existing.setLevel(dto.getLevel());

        Auth updatedAuth = dto.getAuth();
        Auth existingAuth = existing.getAuth();
        existingAuth.setEmail(updatedAuth.getEmail());
        existingAuth.setPassword(updatedAuth.getPassword());
        existingAuth.setRole(updatedAuth.getRole());

        authRepository.save(existingAuth);
        Employee saved = employeeRepository.save(existing);

        dto.setId(saved.getId());
        return dto;
    }

    public String deleteEmployee(int id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(emp);
        return "Employee with id " + id + " deleted successfully.";
    }
}
