//package com.backend.assetmanagement;
//
//import com.backend.assetmanagement.dto.AuditSubmissionDTO;
//import com.backend.assetmanagement.enums.OperationalState;
//import com.backend.assetmanagement.exception.ResourceNotFoundException;
//import com.backend.assetmanagement.model.AuditSubmission;
//import com.backend.assetmanagement.repository.AuditSubmissionRepository;
//import com.backend.assetmanagement.service.AuditSubmissionService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.AfterEach;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class AuditSubmissionServiceTest {
//
//    @Mock
//    private AuditSubmissionRepository auditSubmissionRepository;
//
//    @InjectMocks
//    private AuditSubmissionService auditSubmissionService;
//
//    private AuditSubmission sampleSubmission;
//    private AuditSubmissionDTO sampleDTO;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        sampleSubmission = new AuditSubmission();
//        sampleSubmission.setId(1);
//        sampleSubmission.setAuditId(101);
//        sampleSubmission.setOperationalState(OperationalState.working);
//        sampleSubmission.setComments("All good");
//        sampleSubmission.setSubmittedAt(LocalDateTime.now());
//
//        sampleDTO = new AuditSubmissionDTO();
//        sampleDTO.setId(1);
//        sampleDTO.setAuditId(101);
//        sampleDTO.setOperationalState(OperationalState.working);
//        sampleDTO.setComments("All good");
//        sampleDTO.setSubmittedAt(LocalDateTime.now());
//    }
//
//    @AfterEach
//    public void tearDown() {
//        sampleSubmission = null;
//        sampleDTO = null;
//    }
//
//    @Test
//    public void testCreateAuditSubmission() {
//        when(auditSubmissionRepository.save(any())).thenReturn(sampleSubmission);
//
//        AuditSubmissionDTO result = auditSubmissionService.createAuditSubmission(sampleDTO);
//
//        assertEquals(sampleDTO.getAuditId(), result.getAuditId());
//        assertEquals(sampleDTO.getComments(), result.getComments());
//    }
//
//    @Test
//    public void testGetAuditSubmissionById() {
//        when(auditSubmissionRepository.findById(1)).thenReturn(Optional.of(sampleSubmission));
//
//        AuditSubmissionDTO result = auditSubmissionService.getAuditSubmissionById(1);
//
//        assertEquals(1, result.getId());
//        assertEquals("All good", result.getComments());
//    }
//
//    @Test
//    public void testGetAuditSubmissionById_NotFound() {
//        when(auditSubmissionRepository.findById(1)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> {
//            auditSubmissionService.getAuditSubmissionById(1);
//        });
//    }
//
//    @Test
//    public void testGetAllAuditSubmissions() {
//        when(auditSubmissionRepository.findAll()).thenReturn(Arrays.asList(sampleSubmission));
//
//        List<AuditSubmissionDTO> result = auditSubmissionService.getAllAuditSubmissions();
//
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    public void testDeleteAuditSubmission() {
//        when(auditSubmissionRepository.findById(1)).thenReturn(Optional.of(sampleSubmission));
//
//        String response = auditSubmissionService.deleteAuditSubmission(1);
//
//        assertEquals("Audit submission with ID 1 deleted successfully.", response);
//        verify(auditSubmissionRepository, times(1)).delete(sampleSubmission);
//    }
//
//    @Test
//    public void testUpdateAuditSubmission() {
//        when(auditSubmissionRepository.findById(1)).thenReturn(Optional.of(sampleSubmission));
//        when(auditSubmissionRepository.save(any())).thenReturn(sampleSubmission);
//
//        AuditSubmissionDTO updated = auditSubmissionService.updateAuditSubmission(1, sampleDTO);
//
//        assertEquals(sampleDTO.getAuditId(), updated.getAuditId());
//        assertEquals(sampleDTO.getComments(), updated.getComments());
//    }
//}
