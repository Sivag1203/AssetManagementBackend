package com.backend.assetmanagement.model;

import com.backend.assetmanagement.enums.AuditStatus;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "audit")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private Asset asset;

    @OneToOne
    private Employee employee;
    
    @OneToOne
    @JoinColumn(name="audit_submission")
    private AuditSubmission auditSubmission;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private AuditStatus status = AuditStatus.pending;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public AuditSubmission getAuditSubmission() {
		return auditSubmission;
	}

	public void setAuditSubmission(AuditSubmission auditSubmission) {
		this.auditSubmission = auditSubmission;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public AuditStatus getStatus() {
		return status;
	}

	public void setStatus(AuditStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Audit [id=" + id + ", asset=" + asset + ", employee=" + employee + ", auditSubmission="
				+ auditSubmission + ", dueDate=" + dueDate + ", status=" + status + "]";
	}
	
	
}
