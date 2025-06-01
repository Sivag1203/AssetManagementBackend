package com.backend.assetmanagement.model;

import com.backend.assetmanagement.enums.OperationalState;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_submissions")
public class AuditSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="audit_id")
    private int auditId;

    @Enumerated(EnumType.STRING)
    @Column(name = "operational_state")
    private OperationalState operationalState = OperationalState.working;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(columnDefinition = "TEXT")
    private String comments;

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
	
	@Override
	public String toString() {
		return "AuditSubmission [id=" + id + ", auditId=" + auditId + ", operationalState=" + operationalState
				+ ", submittedAt=" + submittedAt + ", comments=" + comments + "]";
	}
}
