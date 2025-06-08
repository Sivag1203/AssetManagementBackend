package com.backend.assetmanagement;

import com.backend.assetmanagement.dto.EmployeeDTO;
import com.backend.assetmanagement.enums.Level;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.model.Employee;
import com.backend.assetmanagement.repository.AuthRepository;
import com.backend.assetmanagement.repository.EmployeeRepository;
import com.backend.assetmanagement.service.EmployeeService;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AuthRepository authRepository;

    private Employee employee;
    private EmployeeDTO employeeDTO;
    private Auth auth;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        auth = new Auth();
        auth.setId(1);
        auth.setEmail("test@example.com");
        auth.setPassword("pass");
        auth.setRole("EMPLOYEE");

        employee = new Employee();
        employee.setId(1);
        employee.setName("John");
        employee.setEmail("john@example.com");
        employee.setPhone("1234567890");
        employee.setAddress("Chennai");
        employee.setDepartment("IT");
        employee.setLevel(Level.L1);
        employee.setAuth(auth);

        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1);
        employeeDTO.setName("John");
        employeeDTO.setEmail("john@example.com");
        employeeDTO.setPhone("1234567890");
        employeeDTO.setAddress("Chennai");
        employeeDTO.setDepartment("IT");
        employeeDTO.setLevel(Level.L1);
        employeeDTO.setAuth(auth);
    }

    @Test
    public void testAddEmployee() {
        when(authRepository.save(any(Auth.class))).thenReturn(auth);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDTO saved = employeeService.addEmployee(employeeDTO);

        assertEquals("John", saved.getName());
        assertEquals(Level.L1, saved.getLevel());
        verify(authRepository, times(1)).save(auth);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    public void testGetEmployeeById_Valid() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        EmployeeDTO result = employeeService.getEmployeeById(1);

        assertEquals("John", result.getName());
        assertEquals("Chennai", result.getAddress());
    }

    @Test
    public void testGetEmployeeById_Invalid() {
        when(employeeRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(2);
        });
    }

    @Test
    public void testDeleteEmployee_Valid() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        String message = employeeService.deleteEmployee(1);
        assertEquals("Employee with id 1 deleted successfully.", message);
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    public void testDeleteEmployee_Invalid() {
        when(employeeRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.deleteEmployee(2);
        });
    }

    @AfterEach
    public void tearDown() {
        employee = null;
        employeeDTO = null;
        auth = null;
    }
}
