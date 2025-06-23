//package com.backend.assetmanagement;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.any;
//
//import com.backend.assetmanagement.dto.ReturnRequestDTO;
//import com.backend.assetmanagement.enums.ReturnStatus;
//import com.backend.assetmanagement.model.Asset;
//import com.backend.assetmanagement.model.AssignedAsset;
//import com.backend.assetmanagement.model.Employee;
//import com.backend.assetmanagement.model.ReturnRequest;
//import com.backend.assetmanagement.repository.AssetRepository;
//import com.backend.assetmanagement.repository.AssignedAssetRepository;
//import com.backend.assetmanagement.repository.EmployeeRepository;
//import com.backend.assetmanagement.repository.ReturnRequestRepository;
//import com.backend.assetmanagement.service.ReturnRequestService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDate;
//import java.util.Optional;
//import java.util.List;
//import java.util.ArrayList;
//
//@SpringBootTest
//public class ReturnRequestServiceTest {
//
//    @InjectMocks
//    private ReturnRequestService returnRequestService;
//
//    @Mock
//    private ReturnRequestRepository returnRequestRepository;
//
//    @Mock
//    private AssignedAssetRepository assignedAssetRepository;
//
//    @Mock
//    private EmployeeRepository employeeRepository;
//
//    @Mock
//    private AssetRepository assetRepository;
//
//    private ReturnRequest request;
//    private ReturnRequestDTO dto;
//    private Employee employee;
//    private Asset asset;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        employee = new Employee();
//        employee.setId(1);
//        employee.setName("John");
//
//        asset = new Asset();
//        asset.setId(100);
//        asset.setSpecs("Laptop i7");
//
//        request = new ReturnRequest();
//        request.setId(1);
//        request.setEmployee(employee);
//        request.setAsset(asset);
//        request.setRequestDate(LocalDate.now());
//        request.setStatus(ReturnStatus.pending);
//
//        dto = new ReturnRequestDTO();
//        dto.setEmployeeId(1);
//        dto.setAssetId(100);
//    }
////
////    @Test
////    public void testCreateRequest_Success() {
////        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
////        when(assetRepository.findById(100)).thenReturn(Optional.of(asset));
////        when(returnRequestRepository.save(any(ReturnRequest.class))).thenReturn(request);
////
////        ReturnRequestDTO result = returnRequestService.createRequest(dto);
////        assertEquals(1, result.getId());
////        assertEquals("John", result.getEmployeeName());
////        assertEquals("Laptop i7", result.getAssetSpecs());
////        assertEquals(ReturnStatus.pending, result.getStatus());
////    }
//
////    @Test
////    public void testCreateRequest_EmployeeNotFound() {
////        when(employeeRepository.findById(1)).thenReturn(Optional.empty());
////
////        assertThrows(RuntimeException.class, () -> {
////            returnRequestService.createRequest(dto);
////        });
////    }
//
////    @Test
////    public void testGetAllRequests() {
////        List<ReturnRequest> list = new ArrayList<>();
////        list.add(request);
////
////        when(returnRequestRepository.findAll()).thenReturn(list);
////
////        List<ReturnRequest> result = returnRequestService.getAllRequests();
////        assertEquals(1, result.size());
////        assertEquals("John", result.get(0).setEmployee(employee));
////    }
//
//    @Test
//    public void testApproveRequest_Success() {
//        when(returnRequestRepository.findById(1)).thenReturn(Optional.of(request));
//        when(assignedAssetRepository.findByEmployeeAndAsset(1, 100)).thenReturn(new AssignedAsset());
//        when(returnRequestRepository.save(any(ReturnRequest.class))).thenReturn(request);
//
//        ReturnRequestDTO result = returnRequestService.approveRequest(1);
//        assertEquals(ReturnStatus.completed, result.getStatus());
//    }
//
//    @Test
//    public void testRejectRequest_Success() {
//        when(returnRequestRepository.findById(1)).thenReturn(Optional.of(request));
//        String result = returnRequestService.rejectRequest(1);
//        assertEquals("Return request rejected and deleted.", result);
//    }
//
//    @Test
//    public void testGetRequestsByEmployee() {
//        List<ReturnRequest> list = new ArrayList<>();
//        list.add(request);
//        when(returnRequestRepository.findByEmployeeId(1)).thenReturn(list);
//
//        List<ReturnRequestDTO> result = returnRequestService.getRequestsByEmployee(1);
//        assertEquals(1, result.size());
//        assertEquals(1, result.get(0).getEmployeeId());
//    }
//}
