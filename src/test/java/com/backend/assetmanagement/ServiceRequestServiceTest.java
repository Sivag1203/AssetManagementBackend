package com.backend.assetmanagement;

import com.backend.assetmanagement.enums.ServiceStatus;
import com.backend.assetmanagement.model.Asset;
import com.backend.assetmanagement.model.Employee;
import com.backend.assetmanagement.model.ServiceRequest;
import com.backend.assetmanagement.repository.AssetRepository;
import com.backend.assetmanagement.repository.EmployeeRepository;
import com.backend.assetmanagement.repository.ServiceRequestRepository;
import com.backend.assetmanagement.service.ServiceRequestService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ServiceRequestServiceTest {

    @InjectMocks
    private ServiceRequestService service;

    @Mock private ServiceRequestRepository reqRepo;
    @Mock private AssetRepository assetRepo;
    @Mock private EmployeeRepository employeeRepo;

    private AutoCloseable mocks;

    private Employee employee;
    private Asset asset;
    private ServiceRequest stored;

    @BeforeEach
    void init() {
        mocks = MockitoAnnotations.openMocks(this);

        employee = new Employee();
        employee.setId(1);
        employee.setName("John");

        asset = new Asset();
        asset.setId(1);
        asset.setSpecs("Laptop – Dell");

        stored = new ServiceRequest();
        stored.setId(1);
        stored.setEmployee(employee);
        stored.setAsset(asset);
        stored.setDescription("Screen not working");
        stored.setStatus(ServiceStatus.pending);
        stored.setRequestDate(LocalDate.now());
    }

    @AfterEach
    void clean() throws Exception { mocks.close(); }

    @Test
    void create_ok() {
        ServiceRequest body = new ServiceRequest();
        body.setEmployee(employee);
        body.setAsset(asset);
        body.setDescription("Screen not working");

        when(reqRepo.save(any(ServiceRequest.class))).thenReturn(stored);

        ServiceRequest saved = service.createRequest(body);

        assertEquals(1, saved.getId());
        assertEquals(ServiceStatus.pending, saved.getStatus());
        assertEquals("John", saved.getEmployee().getName());
    }

    @Test
    void approve_ok() {
        when(reqRepo.findById(1)).thenReturn(Optional.of(stored));
        when(reqRepo.save(any(ServiceRequest.class))).thenReturn(stored);

        ServiceRequest updated = service.approveRequest(1);

        assertEquals(ServiceStatus.completed, updated.getStatus());
    }

    @Test
    void reject_ok() {
        when(reqRepo.findById(1)).thenReturn(Optional.of(stored));

        String msg = service.rejectRequest(1);

        verify(reqRepo).delete(stored);
        assertEquals("Service request rejected and deleted", msg);
    }

    @Test
    void list_all() {
        when(reqRepo.findAll()).thenReturn(List.of(stored));

        List<ServiceRequest> list = service.getAllRequests();

        assertEquals(1, list.size());
        assertEquals("Laptop – Dell", list.get(0).getAsset().getSpecs());
    }

    @Test
    void list_byEmployee() {
        when(reqRepo.findByEmployeeId(1)).thenReturn(List.of(stored));

        List<ServiceRequest> list = service.getRequestsByEmployee(1);

        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getEmployee().getId());
    }
}
