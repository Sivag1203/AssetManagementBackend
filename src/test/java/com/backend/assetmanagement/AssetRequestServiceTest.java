package com.backend.assetmanagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.backend.assetmanagement.enums.RequestStatus;
import com.backend.assetmanagement.model.Asset;
import com.backend.assetmanagement.model.AssetRequest;
import com.backend.assetmanagement.model.AssignedAsset;
import com.backend.assetmanagement.model.Employee;
import com.backend.assetmanagement.repository.AssetRequestRepository;
import com.backend.assetmanagement.repository.AssignedAssetRepository;
import com.backend.assetmanagement.service.AssetRequestService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AssetRequestServiceTest {

    @Mock
    private AssetRequestRepository assetRequestRepository;

    @Mock
    private AssignedAssetRepository assignedAssetRepository;

    @InjectMocks
    private AssetRequestService assetRequestService;

    private AutoCloseable closeable;

    private AssetRequest assetRequest;
    private Employee employee;
    private Asset asset;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        employee = new Employee();
        employee.setId(1);

        asset = new Asset();
        asset.setId(1);

        assetRequest = new AssetRequest();
        assetRequest.setId(1);
        assetRequest.setEmployee(employee);
        assetRequest.setAsset(asset);
        assetRequest.setStatus(RequestStatus.pending);
        assetRequest.setRequestDate(LocalDate.now());
    }

    @AfterEach
    void tearDown() throws Exception {
        assetRequest = null;
        employee = null;
        asset = null;
        closeable.close();
    }

    @Test
    void testCreateRequest() {
        when(assetRequestRepository.save(any(AssetRequest.class))).thenReturn(assetRequest);

        AssetRequest result = assetRequestService.createRequest(assetRequest);

        assertEquals(RequestStatus.pending, result.getStatus());
        assertNotNull(result.getRequestDate());
        assertEquals(1, result.getEmployee().getId());
    }

    @Test
    void testApproveRequest_Success() {
        when(assetRequestRepository.findById(1)).thenReturn(Optional.of(assetRequest));
        when(assetRequestRepository.save(any(AssetRequest.class))).thenReturn(assetRequest);
        when(assignedAssetRepository.save(any(AssignedAsset.class))).thenReturn(new AssignedAsset());

        AssetRequest result = assetRequestService.approveRequest(1);

        assertEquals(RequestStatus.approved, result.getStatus());
    }

    @Test
    void testApproveRequest_NotFound() {
        when(assetRequestRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            assetRequestService.approveRequest(99);
        });
    }

    @Test
    void testRejectRequest_Success() {
        when(assetRequestRepository.findById(1)).thenReturn(Optional.of(assetRequest));

        String message = assetRequestService.rejectRequest(1);

        verify(assetRequestRepository, times(1)).delete(assetRequest);
        assertEquals("Request rejected and deleted", message);
    }

    @Test
    void testRejectRequest_NotFound() {
        when(assetRequestRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            assetRequestService.rejectRequest(99);
        });
    }

    @Test
    void testGetRequestsByEmployee() {
        when(assetRequestRepository.findByEmployeeId(1)).thenReturn(Arrays.asList(assetRequest));

        List<?> result = assetRequestService.getRequestsByEmployee(1);

        assertEquals(1, result.size());
    }
}
