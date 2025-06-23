//package com.backend.assetmanagement;
//
//import com.backend.assetmanagement.dto.ServiceRequestDTO;
//import com.backend.assetmanagement.enums.ServiceStatus;
//import com.backend.assetmanagement.model.Asset;
//import com.backend.assetmanagement.model.Employee;
//import com.backend.assetmanagement.model.ServiceRequest;
//import com.backend.assetmanagement.repository.AssetRepository;
//import com.backend.assetmanagement.repository.EmployeeRepository;
//import com.backend.assetmanagement.repository.ServiceRequestRepository;
//import com.backend.assetmanagement.service.ServiceRequestService;
//import org.junit.jupiter.api.*;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDate;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class ServiceRequestServiceTest {
//
//    @InjectMocks
//    private ServiceRequestService service;
//
//    @Mock
//    private ServiceRequestRepository requestRepo;
//
//    @Mock
//    private AssetRepository assetRepo;
//
//    @Mock
//    private EmployeeRepository employeeRepo;
//
//    private Employee mockEmployee;
//    private Asset mockAsset;
//    private ServiceRequest mockRequest;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        mockEmployee = new Employee();
//        mockEmployee.setId(1);
//        mockEmployee.setName("John");
//
//        mockAsset = new Asset();
//        mockAsset.setId(1);
//        mockAsset.setSpecs("Laptop - Dell");
//
//        mockRequest = new ServiceRequest();
//        mockRequest.setId(1);
//        mockRequest.setEmployee(mockEmployee);
//        mockRequest.setAsset(mockAsset);
//        mockRequest.setDescription("Screen not working");
//        mockRequest.setStatus(ServiceStatus.pending);
//        mockRequest.setRequestDate(LocalDate.now());
//    }
//
//    @Test
//    void testCreateRequest() {
//        ServiceRequestDTO dto = new ServiceRequestDTO();
//        dto.setEmployeeId(1);
//        dto.setAssetId(1);
//        dto.setDescription("Screen not working");
//
//        when(employeeRepo.findById(1)).thenReturn(Optional.of(mockEmployee));
//        when(assetRepo.findById(1)).thenReturn(Optional.of(mockAsset));
//        when(requestRepo.save(any())).thenReturn(mockRequest);
//
//        ServiceRequestDTO result = service.createRequest(dto);
//
//        assertEquals(1, result.getEmployeeId());
//        assertEquals("Laptop - Dell", result.getAssetSpecs());
//        assertEquals(ServiceStatus.pending, result.getStatus());
//    }
//
////    @Test
////    void testGetAllRequests() {
////        List<ServiceRequest> list = Arrays.asList(mockRequest);
////        when(requestRepo.findAll()).thenReturn(list);
////
////        List<ServiceRequestDTO> result = service.getAllRequests();
////
////        assertEquals(1, result.size());
////        assertEquals("John", result.get(0).getEmployeeName());
////    }
//
//    @Test
//    void testApproveRequest() {
//        mockRequest.setStatus(ServiceStatus.pending);
//
//        when(requestRepo.findById(1)).thenReturn(Optional.of(mockRequest));
//        when(requestRepo.save(any())).thenReturn(mockRequest);
//
//        ServiceRequestDTO result = service.approveRequest(1);
//
//        assertEquals(ServiceStatus.completed, result.getStatus());
//    }
//
//    @Test
//    void testRejectRequest() {
//        when(requestRepo.findById(1)).thenReturn(Optional.of(mockRequest));
//
//        String result = service.rejectRequest(1);
//
//        verify(requestRepo, times(1)).delete(mockRequest);
//        assertEquals("Service request rejected and deleted", result);
//    }
//
//    @Test
//    void testGetRequestsByEmployee() {
//        List<ServiceRequest> list = Arrays.asList(mockRequest);
//        when(requestRepo.findByEmployeeId(1)).thenReturn(list);
//
//        List<ServiceRequestDTO> result = service.getRequestsByEmployee(1);
//
//        assertEquals(1, result.size());
//        assertEquals("John", result.get(0).getEmployeeName());
//    }
//
//    @AfterEach
//    void tearDown() {
//        mockEmployee = null;
//        mockAsset = null;
//        mockRequest = null;
//    }
//}
