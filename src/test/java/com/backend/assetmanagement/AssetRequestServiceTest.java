package com.backend.assetmanagement;

import com.backend.assetmanagement.enums.RequestStatus;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Asset;
import com.backend.assetmanagement.model.AssetRequest;
import com.backend.assetmanagement.model.AssignedAsset;
import com.backend.assetmanagement.model.Employee;
import com.backend.assetmanagement.repository.AssetRequestRepository;
import com.backend.assetmanagement.repository.AssignedAssetRepository;
import com.backend.assetmanagement.service.AssetRequestService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AssetRequestServiceTest {

    @Mock
    private AssetRequestRepository requestRepo;
    @Mock
    private AssignedAssetRepository assignedRepo;

    @InjectMocks
    private AssetRequestService service;

    private AutoCloseable closeable;
    private AssetRequest req;
    private Employee emp;
    private Asset asset;

    @BeforeEach
    void init() {
        closeable = MockitoAnnotations.openMocks(this);

        emp = new Employee();
        emp.setId(1);

        asset = new Asset();
        asset.setId(1);

        req = new AssetRequest();
        req.setId(1);
        req.setEmployee(emp);
        req.setAsset(asset);
        req.setStatus(RequestStatus.pending);
        req.setRequestDate(LocalDate.now());
    }

    @AfterEach
    void clean() throws Exception {
        closeable.close();
    }

    @Test
    void createRequest_success() {
        when(requestRepo.save(any(AssetRequest.class))).thenReturn(req);

        AssetRequest saved = service.createRequest(req);

        assertEquals(RequestStatus.pending, saved.getStatus());
        assertNotNull(saved.getRequestDate());
        assertEquals(1, saved.getEmployee().getId());
    }

    @Test
    void approveRequest_success() {
        when(requestRepo.findById(1)).thenReturn(Optional.of(req));
        when(requestRepo.save(any(AssetRequest.class))).thenReturn(req);
        when(assignedRepo.save(any(AssignedAsset.class))).thenReturn(new AssignedAsset());

        AssetRequest updated = service.approveRequest(1);

        assertEquals(RequestStatus.approved, updated.getStatus());
    }

    @Test
    void approveRequest_notFound() {
        when(requestRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.approveRequest(99));
    }

    @Test
    void rejectRequest_success() {
        when(requestRepo.findById(1)).thenReturn(Optional.of(req));

        String msg = service.rejectRequest(1);

        verify(requestRepo).delete(req);
        assertEquals("Request 1 rejected & removed.", msg);
    }

    @Test
    void rejectRequest_notFound() {
        when(requestRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.rejectRequest(99));
    }

    @Test
    void getRequestsByEmployee() {
        when(requestRepo.findByEmployeeId(1)).thenReturn(Arrays.asList(req));

        List<AssetRequest> list = service.getRequestsByEmployee(1);

        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getId());
    }
}
