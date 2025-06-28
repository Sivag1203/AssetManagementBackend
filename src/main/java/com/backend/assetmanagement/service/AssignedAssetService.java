package com.backend.assetmanagement.service;

import com.backend.assetmanagement.dto.AssignedAssetDTO;
import com.backend.assetmanagement.model.AssignedAsset;
import com.backend.assetmanagement.model.Asset;
import com.backend.assetmanagement.model.Employee;
import com.backend.assetmanagement.repository.AssignedAssetRepository;
import com.backend.assetmanagement.repository.AssetRepository;
import com.backend.assetmanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignedAssetService {

    @Autowired
    private AssignedAssetRepository assignedAssetRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public AssignedAsset assignAsset(AssignedAsset dto) {
        Asset asset = assetRepository.findById(dto.getAsset().getId()).orElseThrow();
        Employee employee = employeeRepository.findById(dto.getEmployee().getId()).orElseThrow();

        AssignedAsset assignedAsset = new AssignedAsset();
        assignedAsset.setAsset(asset);
        assignedAsset.setEmployee(employee);

        AssignedAsset saved = assignedAssetRepository.save(assignedAsset);
        return saved;
    }

    public List<AssignedAsset> getAllAssignedAssets() {
        return assignedAssetRepository.findAll();
    }

    public AssignedAsset getById(int id) {
        AssignedAsset aa = assignedAssetRepository.findById(id).orElseThrow();
        return aa;
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
        AssignedAsset aa = assignedAssetRepository.findByEmployeeAndAsset(employeeId, assetId);
        return aa;
    }
}