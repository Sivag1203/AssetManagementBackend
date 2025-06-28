package com.backend.assetmanagement.service;
import com.backend.assetmanagement.enums.OperationalState;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.AuditSubmission;
import com.backend.assetmanagement.repository.AuditSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditSubmissionService {

    @Autowired
    private AuditSubmissionRepository auditSubmissionRepository;

    public AuditSubmission createAuditSubmission(AuditSubmission submission) {
        submission.setOperationalState(submission.getOperationalState() != null
                ? submission.getOperationalState()
                : OperationalState.working);

        submission.setSubmittedAt(LocalDateTime.now());
        return auditSubmissionRepository.save(submission);
    }

    public List<AuditSubmission> getAllAuditSubmissions() {
        return auditSubmissionRepository.findAll();
    }

    public AuditSubmission getAuditSubmissionById(int id) {
        return auditSubmissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditSubmission not found with ID: " + id));
    }

    public AuditSubmission updateAuditSubmission(int id, AuditSubmission submission) {
        AuditSubmission existing = auditSubmissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditSubmission not found with ID: " + id));

        existing.setAuditId(submission.getAuditId());
        existing.setOperationalState(submission.getOperationalState());
        existing.setComments(submission.getComments());
        existing.setSubmittedAt(LocalDateTime.now());

        return auditSubmissionRepository.save(existing);
    }

    public String deleteAuditSubmission(int id) {
        AuditSubmission existing = auditSubmissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditSubmission not found with ID: " + id));
        auditSubmissionRepository.delete(existing);
        return "Audit submission with ID " + id + " deleted successfully.";
    }

    public AuditSubmission getSubmissionsByAuditId(int auditId) {
        return auditSubmissionRepository.findByAuditId(auditId);
    }

    public List<AuditSubmission> getSubmissionsByEmployeeId(int employeeId) {
        List<AuditSubmission> submissions = auditSubmissionRepository.findByEmployeeId(employeeId);
        if (submissions.isEmpty()) {
            throw new ResourceNotFoundException("No submissions found for employee ID: " + employeeId);
        }
        return submissions;
    }
}