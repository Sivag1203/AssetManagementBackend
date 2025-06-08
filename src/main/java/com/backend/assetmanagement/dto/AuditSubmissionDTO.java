package com.backend.assetmanagement.dto;

import com.backend.assetmanagement.enums.OperationalState;

import java.time.LocalDateTime;

public class AuditSubmissionDTO {
    private int id;
    private int auditId;
    private OperationalState operationalState;
    private LocalDateTime submittedAt;
    private String comments;

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getAuditId() {
        return auditId;
    }
    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }
    public OperationalState getOperationalState() {
        return operationalState;
    }
    public void setOperationalState(OperationalState operationalState) {
        this.operationalState = operationalState;
    }
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
}
