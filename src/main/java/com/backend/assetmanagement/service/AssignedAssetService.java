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

    public AssignedAssetDTO assignAsset(AssignedAssetDTO dto) {
        Asset asset = assetRepository.findById(dto.getAssetId()).orElseThrow();
        Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow();

        AssignedAsset assignedAsset = new AssignedAsset();
        assignedAsset.setAsset(asset);
        assignedAsset.setEmployee(employee);

        AssignedAsset saved = assignedAssetRepository.save(assignedAsset);
        return new AssignedAssetDTO(saved.getId(), saved.getAsset().getId(), saved.getEmployee().getId());
    }

    public List<AssignedAssetDTO> getAllAssignedAssets() {
        return assignedAssetRepository.findAll().stream()
                .map(aa -> new AssignedAssetDTO(aa.getId(), aa.getAsset().getId(), aa.getEmployee().getId()))
                .collect(Collectors.toList());
    }

    public AssignedAssetDTO getById(int id) {
        AssignedAsset aa = assignedAssetRepository.findById(id).orElseThrow();
        return new AssignedAssetDTO(aa.getId(), aa.getAsset().getId(), aa.getEmployee().getId());
    }

    public String deleteAssignedAsset(int id) {
        assignedAssetRepository.deleteById(id);
        return "Assigned asset with ID " + id + " has been deleted.";
    }

    public List<AssignedAssetDTO> getByEmployeeId(int employeeId) {
        return assignedAssetRepository.findByEmployeeId(employeeId).stream()
                .map(aa -> new AssignedAssetDTO(aa.getId(), aa.getAsset().getId(), aa.getEmployee().getId()))
                .collect(Collectors.toList());
    }

    public List<AssignedAssetDTO> getByAssetId(int assetId) {
        return assignedAssetRepository.findByAssetId(assetId).stream()
                .map(aa -> new AssignedAssetDTO(aa.getId(), aa.getAsset().getId(), aa.getEmployee().getId()))
                .collect(Collectors.toList());
    }

    public AssignedAssetDTO getByEmployeeAndAsset(int employeeId, int assetId) {
        AssignedAsset aa = assignedAssetRepository.findByEmployeeAndAsset(employeeId, assetId);
        return new AssignedAssetDTO(aa.getId(), aa.getAsset().getId(), aa.getEmployee().getId());
    }
}
