package com.backend.assetmanagement.controller;import com.backend.assetmanagement.model.Audit;

import com.backend.assetmanagement.model.AuditSubmission;
import com.backend.assetmanagement.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @PostMapping("/create/{assetId}/{employeeId}")
    public Audit createAudit(@PathVariable int assetId, @PathVariable int employeeId, @RequestBody Audit audit) {
        return auditService.createAudit(assetId, employeeId, audit);
    }

    @PostMapping("/submit/{auditId}")
    public Audit submitAudit(@PathVariable int auditId, @RequestBody AuditSubmission submission) {
        return auditService.submitAudit(auditId, submission);
    }

    @GetMapping("/all")
    public List<Audit> getAllAudits() {
        return auditService.getAllAudits();
    }

    @GetMapping("/{id}")
    public Audit getAuditById(@PathVariable int id) {
        return auditService.getAuditById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAudit(@PathVariable int id) {
        return auditService.deleteAudit(id);
    }

    @GetMapping("/pending")
    public List<Audit> getPendingAudits() {
        return auditService.getPendingAudits();
    }

    @GetMapping("/employee/{employeeId}")
    public List<Audit> getAuditsByEmployee(@PathVariable int employeeId) {
        return auditService.getAuditsByEmployee(employeeId);
    }
}
