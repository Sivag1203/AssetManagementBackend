package com.backend.assetmanagement.dto;

import com.backend.assetmanagement.enums.ReturnStatus;
import java.time.LocalDate;

public class ReturnRequestDTO {
    private int id;
    private int assetId;
    private String assetSpecs;
    private int employeeId;
    private String employeeName;
    private ReturnStatus status;
    private LocalDate requestDate;
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
	public String getAssetSpecs() {
		return assetSpecs;
	}
	public void setAssetSpecs(String assetSpecs) {
		this.assetSpecs = assetSpecs;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public ReturnStatus getStatus() {
		return status;
	}
	public void setStatus(ReturnStatus status) {
		this.status = status;
	}
	public LocalDate getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}
}
