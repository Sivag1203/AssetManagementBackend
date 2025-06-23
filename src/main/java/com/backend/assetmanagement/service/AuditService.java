package com.backend.assetmanagement.service;

import com.backend.assetmanagement.enums.AuditStatus;
import com.backend.assetmanagement.model.*;
import com.backend.assetmanagement.repository.*;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AuditSubmissionRepository auditSubmissionRepository;

    public Audit createAudit(int assetId, int employeeId, Audit audit) {
        Optional<Audit> existingAudit = auditRepository.findByAssetIdAndEmployeeId(assetId, employeeId);
        if (existingAudit.isPresent()) {
            throw new IllegalStateException("Audit already exists for this asset and employee");
        }

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id " + assetId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));

        audit.setAsset(asset);
        audit.setEmployee(employee);
        audit.setStatus(AuditStatus.pending);
        return auditRepository.save(audit);
    }


    public Audit submitAudit(int auditId, AuditSubmission submission) {
        Audit audit = auditRepository.findById(auditId)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found with id " + auditId));

        submission.setSubmittedAt(LocalDateTime.now());
        AuditSubmission savedSubmission = auditSubmissionRepository.save(submission);

        audit.setAuditSubmission(savedSubmission);
        audit.setStatus(AuditStatus.submitted);
        return auditRepository.save(audit);
    }

    public List<Audit> getAllAudits() {
        return auditRepository.findAll();
    }

    public Audit getAuditById(int id) {
        return auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found with id " + id));
    }

    public List<Audit> getPendingAudits() {
        return auditRepository.findPendingAudits();
    }

    public List<Audit> getAuditsByEmployee(int employeeId) {
        return auditRepository.findAuditsByEmployeeId(employeeId);
    }

    public String deleteAudit(int id) {
        Audit audit = auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found with id " + id));
        auditRepository.delete(audit);
        return "Audit deleted with id " + id;
    }
}
