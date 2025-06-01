package com.backend.assetmanagement.service;

import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.model.Employee;
import com.backend.assetmanagement.repository.AuthRepository;
import com.backend.assetmanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;

    public EmployeeService(EmployeeRepository employeeRepository, AuthRepository authRepository) {
        this.employeeRepository = employeeRepository;
        this.authRepository = authRepository;
    }

    public Employee addEmployee(Employee employee) {
        Auth auth = employee.getAuth();
        Auth savedAuth = authRepository.save(auth);
        employee.setAuth(savedAuth);
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setPhone(updatedEmployee.getPhone());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setAddress(updatedEmployee.getAddress());
        existingEmployee.setLevel(updatedEmployee.getLevel());

        // Update Auth details
        Auth updatedAuth = updatedEmployee.getAuth();
        Auth existingAuth = existingEmployee.getAuth();

        existingAuth.setEmail(updatedAuth.getEmail());
        existingAuth.setPassword(updatedAuth.getPassword());
        existingAuth.setRole(updatedAuth.getRole());

        authRepository.save(existingAuth);
        return employeeRepository.save(existingEmployee);
    }

    public String deleteEmployee(int id) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(existingEmployee);
        return "Employee with id " + id + " deleted successfully.";
    }
}
