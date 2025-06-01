package com.backend.assetmanagement.controller;

import com.backend.assetmanagement.model.AuditSubmission;
import com.backend.assetmanagement.service.AuditSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-submission")
public class AuditSubmissionController {

    @Autowired
    private AuditSubmissionService auditSubmissionService;

    @PostMapping("/add")
    public AuditSubmission createAuditSubmission(@RequestBody AuditSubmission submission) {
        return auditSubmissionService.createAuditSubmission(submission);
    }

    @GetMapping("/all")
    public List<AuditSubmission> getAllSubmissions() {
        return auditSubmissionService.getAllAuditSubmissions();
    }

    @GetMapping("/{id}")
    public AuditSubmission getById(@PathVariable int id) {
        return auditSubmissionService.getAuditSubmissionById(id);
    }

    @PutMapping("/update/{id}")
    public AuditSubmission updateSubmission(@PathVariable int id, @RequestBody AuditSubmission updated) {
        return auditSubmissionService.updateAuditSubmission(id, updated);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSubmission(@PathVariable int id) {
        return auditSubmissionService.deleteAuditSubmission(id);
    }
    
    @GetMapping("/audit/{auditId}")
    public AuditSubmission getByAuditId(@PathVariable int auditId) {
        return auditSubmissionService.getSubmissionsByAuditId(auditId);
    }
    
    @GetMapping("/employee/{employeeId}")
    public List<AuditSubmission> getByEmployeeId(@PathVariable int employeeId) {
        return auditSubmissionService.getSubmissionsByEmployeeId(employeeId);
    }
    
}
