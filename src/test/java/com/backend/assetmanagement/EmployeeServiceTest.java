package com.backend.assetmanagement;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @InjectMocks private EmployeeService employeeService;

    @Mock private EmployeeRepository employeeRepository;
    @Mock private AuthRepository      authRepository;
    @Mock private PasswordEncoder     passwordEncoder;

    private AutoCloseable mocks;
    private Auth auth;
    private Employee employee;
    private Employee request;

    @BeforeEach
    void init() {
        mocks = MockitoAnnotations.openMocks(this);

        auth = new Auth();
        auth.setId(1);
        auth.setEmail("john@ex.com");
        auth.setPassword("plain");
        auth.setRole("EMPLOYEE");

        employee = new Employee();
        employee.setId(1);
        employee.setName("John");
        employee.setEmail("john@ex.com");
        employee.setPhone("123");
        employee.setAddress("Chennai");
        employee.setDepartment("IT");
        employee.setLevel(Level.L1);
        employee.setAuth(auth);

        request = new Employee();
        request.setName("John");
        request.setEmail("john@ex.com");
        request.setPhone("123");
        request.setAddress("Chennai");
        request.setDepartment("IT");
        request.setLevel(Level.L1);
        request.setAuth(auth);
    }

    @AfterEach
    void close() throws Exception {
        mocks.close();
    }

    @Test
    void addEmployee_ok() {
        when(passwordEncoder.encode("plain")).thenReturn("encPwd");
        when(authRepository.save(any(Auth.class))).thenAnswer(i -> i.getArgument(0));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(i -> {
            Employee e = i.getArgument(0);
            e.setId(1);
            return e;
        });

        Employee saved = employeeService.addEmployee(request);

        assertEquals("John", saved.getName());
        assertEquals(Level.L1, saved.getLevel());
        assertEquals("encPwd", saved.getAuth().getPassword());
        verify(passwordEncoder).encode("plain");
        verify(authRepository).save(auth);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void getById_ok() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Employee found = employeeService.getEmployeeById(1);
        assertEquals("John", found.getName());
    }

    @Test
    void getById_notFound() {
        when(employeeRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                     () -> employeeService.getEmployeeById(2));
    }

    @Test
    void delete_ok() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        String msg = employeeService.deleteEmployee(1);
        assertEquals("Employee with id 1 deleted successfully.", msg);
        verify(employeeRepository).delete(employee);
    }

    @Test
    void delete_notFound() {
        when(employeeRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                     () -> employeeService.deleteEmployee(2));
    }
}
