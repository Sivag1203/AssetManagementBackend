package com.backend.assetmanagement.dto;

import com.backend.assetmanagement.enums.AuditStatus;
import java.time.LocalDate;

public class AuditDTO {
    private int id;
    private int assetId;
    private int employeeId;
    private int auditSubmissionId;
    private LocalDate dueDate;
    private AuditStatus status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getAuditSubmissionId() {
		return auditSubmissionId;
	}
	public void setAuditSubmissionId(int auditSubmissionId) {
		this.auditSubmissionId = auditSubmissionId;
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
}
