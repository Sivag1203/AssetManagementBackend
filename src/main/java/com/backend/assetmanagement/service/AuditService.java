package com.backend.assetmanagement.service;

import com.backend.assetmanagement.dto.AuditDTO;
import com.backend.assetmanagement.enums.AuditStatus;
import com.backend.assetmanagement.model.*;
import com.backend.assetmanagement.repository.*;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    
    public AuditDTO createAudit(int assetId, int employeeId, Audit audit) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id " + assetId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));

        audit.setAsset(asset);
        audit.setEmployee(employee);
        audit.setStatus(AuditStatus.pending);
        Audit savedAudit = auditRepository.save(audit);
        return convertToDTO(savedAudit);
    }

    public AuditDTO submitAudit(int auditId, AuditSubmission submission) {
        Audit audit = auditRepository.findById(auditId)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found with id " + auditId));

        submission.setSubmittedAt(LocalDateTime.now());
        AuditSubmission savedSubmission = auditSubmissionRepository.save(submission);
        audit.setAuditSubmission(savedSubmission);
        audit.setStatus(AuditStatus.submitted);
        return convertToDTO(auditRepository.save(audit));
    }

    public List<AuditDTO> getAllAudits() {
        return auditRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public AuditDTO getAuditById(int id) {
        return convertToDTO(auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found with id " + id)));
    }

    public List<AuditDTO> getPendingAudits() {
        return auditRepository.findPendingAudits()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<AuditDTO> getAuditsByEmployee(int employeeId) {
        return auditRepository.findAuditsByEmployeeId(employeeId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    
    public String deleteAudit(int id) {
        Audit audit = auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found with id " + id));
        auditRepository.delete(audit);
        return "Audit deleted with id " + id;
    }
    
    private AuditDTO convertToDTO(Audit audit) {
        AuditDTO dto = new AuditDTO();
        dto.setId(audit.getId());
        dto.setAssetId(audit.getAsset().getId());
        dto.setEmployeeId(audit.getEmployee().getId());
        dto.setDueDate(audit.getDueDate());
        dto.setStatus(audit.getStatus());
        if (audit.getAuditSubmission() != null)
            dto.setAuditSubmissionId(audit.getAuditSubmission().getId());
        return dto;
    }
}
