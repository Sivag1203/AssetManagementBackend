package com.backend.assetmanagement.controller;import com.backend.assetmanagement.model.Audit;

import com.backend.assetmanagement.model.AuditSubmission;
import com.backend.assetmanagement.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.backend.assetmanagement.dto.AuditDTO;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @PostMapping("/create/{assetId}/{employeeId}")
    public AuditDTO createAudit(@PathVariable int assetId, @PathVariable int employeeId, @RequestBody Audit audit) {
        return auditService.createAudit(assetId, employeeId, audit);
    }

    @PostMapping("/submit/{auditId}")
    public AuditDTO submitAudit(@PathVariable int auditId, @RequestBody AuditSubmission submission) {
        return auditService.submitAudit(auditId, submission);
    }

    @GetMapping("/all")
    public List<AuditDTO> getAllAudits() {
        return auditService.getAllAudits();
    }

    @GetMapping("/{id}")
    public AuditDTO getAuditById(@PathVariable int id) {
        return auditService.getAuditById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAudit(@PathVariable int id) {
        return auditService.deleteAudit(id);
    }

    @GetMapping("/pending")
    public List<AuditDTO> getPendingAudits() {
        return auditService.getPendingAudits();
    }

    @GetMapping("/employee/{employeeId}")
    public List<AuditDTO> getAuditsByEmployee(@PathVariable int employeeId) {
        return auditService.getAuditsByEmployee(employeeId);
    }
}
