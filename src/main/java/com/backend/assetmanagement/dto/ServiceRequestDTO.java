package com.backend.assetmanagement.dto;

import com.backend.assetmanagement.enums.ServiceStatus;
import java.time.LocalDate;

public class ServiceRequestDTO {
    private int id;
    private int assetId;
    private String assetSpecs;
    private int employeeId;
    private String employeeName;
    private String description;
    private ServiceStatus status;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ServiceStatus getStatus() {
		return status;
	}
	public void setStatus(ServiceStatus status) {
		this.status = status;
	}
	public LocalDate getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

}
