package com.backend.assetmanagement.repository;

import com.backend.assetmanagement.model.AuditSubmission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AuditSubmissionRepository extends JpaRepository<AuditSubmission, Integer> {

    @Query("SELECT a FROM AuditSubmission a WHERE a.auditId = ?1")
    AuditSubmission findByAuditId(int auditId);
    
    @Query("SELECT a FROM AuditSubmission a JOIN Audit audit ON a.auditId = audit.id WHERE audit.employee.id = ?1")
    	List<AuditSubmission> findByEmployeeId(int employeeId);
    
}
