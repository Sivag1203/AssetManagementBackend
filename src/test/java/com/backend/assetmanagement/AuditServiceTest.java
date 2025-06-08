package com.backend.assetmanagement;

import com.backend.assetmanagement.dto.AuditDTO;
import com.backend.assetmanagement.enums.AuditStatus;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.*;
import com.backend.assetmanagement.repository.*;
import com.backend.assetmanagement.service.AuditService;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuditServiceTest {

    @InjectMocks
    private AuditService auditService;

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AuditSubmissionRepository auditSubmissionRepository;

    private Asset asset;
    private Employee employee;
    private Audit audit;
    private AuditSubmission submission;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        asset = new Asset();
        asset.setId(1);

        employee = new Employee();
        employee.setId(1);

        audit = new Audit();
        audit.setId(1);
        audit.setAsset(asset);
        audit.setEmployee(employee);
        audit.setDueDate(LocalDate.now().plusDays(7));
        audit.setStatus(AuditStatus.pending);

        submission = new AuditSubmission();
        submission.setId(1);
    }

    @Test
    public void testCreateAudit() {
        when(assetRepository.findById(1)).thenReturn(Optional.of(asset));
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(auditRepository.save(any(Audit.class))).thenReturn(audit);

        AuditDTO result = auditService.createAudit(1, 1, new Audit());

        assertEquals(1, result.getAssetId());
        assertEquals(1, result.getEmployeeId());
        assertEquals(AuditStatus.pending, result.getStatus());
    }

    @Test
    public void testCreateAudit_AssetNotFound() {
        when(assetRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            auditService.createAudit(1, 1, new Audit());
        });
    }

    @Test
    public void testSubmitAudit() {
        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
        when(auditSubmissionRepository.save(any(AuditSubmission.class))).thenReturn(submission);
        when(auditRepository.save(any(Audit.class))).thenReturn(audit);

        AuditDTO result = auditService.submitAudit(1, submission);

        assertEquals(1, result.getAuditSubmissionId());
        assertEquals(AuditStatus.submitted, result.getStatus());
    }

    @Test
    public void testGetAuditById() {
        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));

        AuditDTO result = auditService.getAuditById(1);

        assertEquals(1, result.getId());
    }

    @Test
    public void testGetAllAudits() {
        when(auditRepository.findAll()).thenReturn(List.of(audit));

        List<AuditDTO> result = auditService.getAllAudits();

        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteAudit() {
        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));

        String result = auditService.deleteAudit(1);

        assertEquals("Audit deleted with id 1", result);
        verify(auditRepository, times(1)).delete(audit);
    }

    @AfterEach
    public void cleanUp() {
        asset = null;
        employee = null;
        audit = null;
        submission = null;
    }
}
