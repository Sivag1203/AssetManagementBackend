package com.backend.assetmanagement.controller;

import com.backend.assetmanagement.dto.AuditSubmissionDTO;
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
    public AuditSubmissionDTO createAuditSubmission(@RequestBody AuditSubmissionDTO dto) {
        return auditSubmissionService.createAuditSubmission(dto);
    }

    @GetMapping("/all")
    public List<AuditSubmissionDTO> getAllSubmissions() {
        return auditSubmissionService.getAllAuditSubmissions();
    }

    @GetMapping("/{id}")
    public AuditSubmissionDTO getById(@PathVariable int id) {
        return auditSubmissionService.getAuditSubmissionById(id);
    }

    @PutMapping("/update/{id}")
    public AuditSubmissionDTO updateSubmission(@PathVariable int id, @RequestBody AuditSubmissionDTO dto) {
        return auditSubmissionService.updateAuditSubmission(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSubmission(@PathVariable int id) {
        return auditSubmissionService.deleteAuditSubmission(id);
    }

    @GetMapping("/audit/{auditId}")
    public AuditSubmissionDTO getByAuditId(@PathVariable int auditId) {
        return auditSubmissionService.getSubmissionsByAuditId(auditId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<AuditSubmissionDTO> getByEmployeeId(@PathVariable int employeeId) {
        return auditSubmissionService.getSubmissionsByEmployeeId(employeeId);
    }
}
