package com.backend.assetmanagement;

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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuditServiceTest {

    @Mock  private AuditRepository           auditRepo;
    @Mock  private AuditSubmissionRepository submissionRepo;
    @Mock  private AssetRepository           assetRepo;
    @Mock  private EmployeeRepository        employeeRepo;

    @InjectMocks
    private AuditService service;

    private AutoCloseable closeable;
    private Asset asset;
    private Employee emp;
    private Audit audit;
    private AuditSubmission sub;

    @BeforeEach
    void init() {
        closeable = MockitoAnnotations.openMocks(this);

        asset = new Asset();
        asset.setId(1);

        emp = new Employee();
        emp.setId(1);

        audit = new Audit();
        audit.setId(1);
        audit.setAsset(asset);
        audit.setEmployee(emp);
        audit.setDueDate(LocalDate.now().plusDays(5));
        audit.setStatus(AuditStatus.pending);

        sub = new AuditSubmission();
        sub.setId(11);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void createAudit_success() {
        when(assetRepo.findById(1)).thenReturn(Optional.of(asset));
        when(employeeRepo.findById(1)).thenReturn(Optional.of(emp));
        when(auditRepo.save(any(Audit.class))).thenReturn(audit);

        Audit saved = service.createAudit(1, 1, new Audit());

        assertEquals(1, saved.getAsset().getId());
        assertEquals(1, saved.getEmployee().getId());
        assertEquals(AuditStatus.pending, saved.getStatus());
    }

    @Test
    void createAudit_assetNotFound() {
        when(assetRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.createAudit(1, 1, new Audit()));
    }

    @Test
    void submitAudit_success() {
        when(auditRepo.findById(1)).thenReturn(Optional.of(audit));
        when(submissionRepo.save(any(AuditSubmission.class))).thenReturn(sub);
        when(auditRepo.save(any(Audit.class))).thenReturn(audit);

        Audit result = service.submitAudit(1, new AuditSubmission());

        assertEquals(AuditStatus.submitted, result.getStatus());
        assertEquals(11, result.getAuditSubmission().getId());
    }

    @Test
    void getAuditById() {
        when(auditRepo.findById(1)).thenReturn(Optional.of(audit));

        Audit result = service.getAuditById(1);

        assertEquals(1, result.getId());
    }

    @Test
    void getAllAudits() {
        when(auditRepo.findAll()).thenReturn(List.of(audit));

        List<Audit> list = service.getAllAudits();

        assertEquals(1, list.size());
    }

    @Test
    void deleteAudit() {
        when(auditRepo.findById(1)).thenReturn(Optional.of(audit));

        String msg = service.deleteAudit(1);

        assertEquals("Audit deleted with id 1", msg);
        verify(auditRepo).delete(audit);
    }
}
