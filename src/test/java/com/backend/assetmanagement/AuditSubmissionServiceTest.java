package com.backend.assetmanagement;

import com.backend.assetmanagement.enums.OperationalState;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.AuditSubmission;
import com.backend.assetmanagement.repository.AuditSubmissionRepository;
import com.backend.assetmanagement.service.AuditSubmissionService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuditSubmissionServiceTest {

    @Mock  private AuditSubmissionRepository repo;
    @InjectMocks private AuditSubmissionService service;

    private AutoCloseable mocks;
    private AuditSubmission sub;

    @BeforeEach
    void init() {
        mocks = MockitoAnnotations.openMocks(this);
        sub = new AuditSubmission();
        sub.setId(1);
        sub.setAuditId(77);
        sub.setOperationalState(OperationalState.working);
        sub.setComments("ok");
        sub.setSubmittedAt(LocalDateTime.now());
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void create_ok() {
        when(repo.save(any(AuditSubmission.class))).thenReturn(sub);
        AuditSubmission saved = service.createAuditSubmission(sub);
        assertEquals(77, saved.getAuditId());
        verify(repo).save(sub);
    }

    @Test
    void getById_ok() {
        when(repo.findById(1)).thenReturn(Optional.of(sub));
        AuditSubmission found = service.getAuditSubmissionById(1);
        assertEquals(1, found.getId());
    }

    @Test
    void getById_notFound() {
        when(repo.findById(2)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getAuditSubmissionById(2));
    }

    @Test
    void getAll() {
        when(repo.findAll()).thenReturn(singletonList(sub));
        List<AuditSubmission> list = service.getAllAuditSubmissions();
        assertEquals(1, list.size());
    }

    @Test
    void update_ok() {
        when(repo.findById(1)).thenReturn(Optional.of(sub));
        when(repo.save(any(AuditSubmission.class))).thenReturn(sub);
        AuditSubmission updated = service.updateAuditSubmission(1, sub);
        assertEquals("ok", updated.getComments());
    }

    @Test
    void delete_ok() {
        when(repo.findById(1)).thenReturn(Optional.of(sub));
        String msg = service.deleteAuditSubmission(1);
        assertEquals("Audit submission with ID 1 deleted successfully.", msg);
        verify(repo).delete(sub);
    }
}
