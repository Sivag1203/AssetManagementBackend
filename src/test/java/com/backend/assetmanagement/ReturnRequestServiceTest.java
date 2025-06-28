package com.backend.assetmanagement;

import com.backend.assetmanagement.enums.ReturnStatus;
import com.backend.assetmanagement.model.Asset;
import com.backend.assetmanagement.model.AssignedAsset;
import com.backend.assetmanagement.model.Employee;
import com.backend.assetmanagement.model.ReturnRequest;
import com.backend.assetmanagement.repository.AssignedAssetRepository;
import com.backend.assetmanagement.repository.ReturnRequestRepository;
import com.backend.assetmanagement.service.ReturnRequestService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReturnRequestServiceTest {

    @InjectMocks private ReturnRequestService service;

    @Mock private ReturnRequestRepository reqRepo;
    @Mock private AssignedAssetRepository  assignedRepo;

    private AutoCloseable mocks;

    private Employee      emp;
    private Asset         asset;
    private ReturnRequest entity;
    private ReturnRequest payload;

    @BeforeEach
    void init() {
        mocks = MockitoAnnotations.openMocks(this);

        emp   = new Employee(); emp.setId(1);
        asset = new Asset();    asset.setId(100);

        entity = new ReturnRequest();
        entity.setId(1);
        entity.setEmployee(emp);
        entity.setAsset(asset);
        entity.setStatus(ReturnStatus.pending);
        entity.setRequestDate(LocalDate.now());

        payload = new ReturnRequest();
        payload.setEmployee(emp);
        payload.setAsset(asset);
    }

    @AfterEach
    void close() throws Exception { mocks.close(); }

    @Test
    void create_ok() {
        when(reqRepo.save(any(ReturnRequest.class))).thenAnswer(inv -> {
            ReturnRequest r = inv.getArgument(0);
            r.setId(1);
            return r;
        });

        ReturnRequest saved = service.createRequest(payload);

        assertEquals(1, saved.getId());
        assertEquals(ReturnStatus.pending, saved.getStatus());
        verify(reqRepo).save(any(ReturnRequest.class));
    }

    @Test
    void getAll_ok() {
        when(reqRepo.findAll()).thenReturn(List.of(entity));

        List<ReturnRequest> list = service.getAllRequests();

        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getEmployee().getId());
    }

    @Test
    void byEmployee_ok() {
        when(reqRepo.findByEmployeeId(1)).thenReturn(List.of(entity));

        List<ReturnRequest> list = service.getRequestsByEmployee(1);

        assertEquals(1, list.size());
        assertEquals(100, list.get(0).getAsset().getId());
    }

    @Test
    void approve_ok() {
        when(reqRepo.findById(1)).thenReturn(Optional.of(entity));
        when(reqRepo.save(any(ReturnRequest.class))).thenReturn(entity);
        when(assignedRepo.findByEmployeeAndAsset(1, 100)).thenReturn(new AssignedAsset());

        ReturnRequest res = service.approveRequest(1);

        assertEquals(ReturnStatus.completed, res.getStatus());
        verify(assignedRepo).delete(any(AssignedAsset.class));
    }

    @Test
    void reject_ok() {
        when(reqRepo.findById(1)).thenReturn(Optional.of(entity));

        String msg = service.rejectRequest(1);

        assertEquals("Return request rejected and deleted.", msg);
        verify(reqRepo).delete(entity);
    }
}
