package com.backend.assetmanagement.service;

import com.backend.assetmanagement.dto.AuditSubmissionDTO;
import com.backend.assetmanagement.enums.OperationalState;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.AuditSubmission;
import com.backend.assetmanagement.repository.AuditSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditSubmissionService {

    @Autowired
    private AuditSubmissionRepository auditSubmissionRepository;

    public AuditSubmissionDTO createAuditSubmission(AuditSubmissionDTO dto) {
        AuditSubmission submission = new AuditSubmission();
        submission.setAuditId(dto.getAuditId());
        submission.setOperationalState(dto.getOperationalState() != null ? dto.getOperationalState() : OperationalState.working);
        submission.setComments(dto.getComments());
        submission.setSubmittedAt(LocalDateTime.now());

        AuditSubmission saved = auditSubmissionRepository.save(submission);
        return convertToDTO(saved);
    }

    public List<AuditSubmissionDTO> getAllAuditSubmissions() {
        return auditSubmissionRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AuditSubmissionDTO getAuditSubmissionById(int id) {
        AuditSubmission submission = auditSubmissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditSubmission not found with ID: " + id));
        return convertToDTO(submission);
    }

    public AuditSubmissionDTO updateAuditSubmission(int id, AuditSubmissionDTO dto) {
        AuditSubmission existing = auditSubmissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditSubmission not found with ID: " + id));

        existing.setAuditId(dto.getAuditId());
        existing.setOperationalState(dto.getOperationalState());
        existing.setComments(dto.getComments());
        existing.setSubmittedAt(LocalDateTime.now());

        return convertToDTO(auditSubmissionRepository.save(existing));
    }

    public String deleteAuditSubmission(int id) {
        AuditSubmission existing = auditSubmissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditSubmission not found with ID: " + id));
        auditSubmissionRepository.delete(existing);
        return "Audit submission with ID " + id + " deleted successfully.";
    }

    public AuditSubmissionDTO getSubmissionsByAuditId(int auditId) {
        AuditSubmission submission = auditSubmissionRepository.findByAuditId(auditId);
        return convertToDTO(submission);
    }

    public List<AuditSubmissionDTO> getSubmissionsByEmployeeId(int employeeId) {
        List<AuditSubmission> submissions = auditSubmissionRepository.findByEmployeeId(employeeId);
        if (submissions.isEmpty()) {
            throw new ResourceNotFoundException("No submissions found for employee ID: " + employeeId);
        }
        return submissions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private AuditSubmissionDTO convertToDTO(AuditSubmission submission) {
        AuditSubmissionDTO dto = new AuditSubmissionDTO();
        dto.setId(submission.getId());
        dto.setAuditId(submission.getAuditId());
        dto.setOperationalState(submission.getOperationalState());
        dto.setSubmittedAt(submission.getSubmittedAt());
        dto.setComments(submission.getComments());
        return dto;
    }
}
