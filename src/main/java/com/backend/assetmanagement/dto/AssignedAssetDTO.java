package com.backend.assetmanagement.dto;

import org.springframework.stereotype.Component;

@Component
public class AssignedAssetDTO {
    private int id;
    private int assetId;
    private int employeeId;

    public AssignedAssetDTO() {}

    public AssignedAssetDTO(int id, int assetId, int employeeId) {
        this.id = id;
        this.assetId = assetId;
        this.employeeId = employeeId;
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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
