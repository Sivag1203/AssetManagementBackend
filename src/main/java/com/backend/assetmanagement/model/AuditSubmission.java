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

    @ManyToOne(optional = false)
    private Audit audit;

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

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
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
        return "AuditSubmission [id=" + id + ", audit=" + audit + ", operationalState=" + operationalState +
               ", submittedAt=" + submittedAt + ", comments=" + comments + "]";
    }
}
