package com.backend.assetmanagement.service;

import com.backend.assetmanagement.model.AssignedAsset;
import com.backend.assetmanagement.repository.AssignedAssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignedAssetService {

    @Autowired
    private AssignedAssetRepository assignedAssetRepository;

    public AssignedAsset assignAsset(AssignedAsset assignedAsset) {
        return assignedAssetRepository.save(assignedAsset);
    }

    public List<AssignedAsset> getAllAssignedAssets() {
        return assignedAssetRepository.findAll();
    }

    public AssignedAsset getById(int id) {
        return assignedAssetRepository.findById(id).orElse(null);
    }

    public String deleteAssignedAsset(int id) {
        assignedAssetRepository.deleteById(id);
        return "Assigned asset with ID " + id + " has been deleted.";
    }

    public List<AssignedAsset> getByEmployeeId(int employeeId) {
        return assignedAssetRepository.findByEmployeeId(employeeId);
    }

    public List<AssignedAsset> getByAssetId(int assetId) {
        return assignedAssetRepository.findByAssetId(assetId);
    }

    public AssignedAsset getByEmployeeAndAsset(int employeeId, int assetId) {
        return assignedAssetRepository.findByEmployeeAndAsset(employeeId, assetId);
    }
}
