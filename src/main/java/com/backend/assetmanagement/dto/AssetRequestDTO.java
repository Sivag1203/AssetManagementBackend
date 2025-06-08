package com.backend.assetmanagement.dto;

import com.backend.assetmanagement.enums.RequestStatus;
import com.backend.assetmanagement.model.AssetRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AssetRequestDTO {
    private int id;
    private int assetId;
    private String assetSerialNumber;
    private int employeeId;
    private String employeeName;
    private RequestStatus status;
    private LocalDate requestDate;

    public AssetRequestDTO() {}

    public AssetRequestDTO(int id, int assetId, String assetSerialNumber, int employeeId,
                           String employeeName, RequestStatus status, LocalDate requestDate) {
        this.id = id;
        this.assetId = assetId;
        this.assetSerialNumber = assetSerialNumber;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.status = status;
        this.requestDate = requestDate;
    }

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

	public String getAssetSerialNumber() {
		return assetSerialNumber;
	}

	public void setAssetSerialNumber(String assetSerialNumber) {
		this.assetSerialNumber = assetSerialNumber;
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

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public static List<AssetRequestDTO> convertToDTOList(List<AssetRequest> requests) {
        List<AssetRequestDTO> dtoList = new ArrayList<>();
        for (AssetRequest r : requests) {
            dtoList.add(new AssetRequestDTO(
                r.getId(),
                r.getAsset().getId(),
                r.getAsset().getSerialNumber(),
                r.getEmployee().getId(),
                r.getEmployee().getName(),
                r.getStatus(),
                r.getRequestDate()
            ));
        }
        return dtoList;
    }
}
