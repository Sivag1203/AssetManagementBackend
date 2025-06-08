package com.backend.assetmanagement.controller;

import com.backend.assetmanagement.dto.AssignedAssetDTO;
import com.backend.assetmanagement.service.AssignedAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assigned-assets")
public class AssignedAssetController {

    @Autowired
    private AssignedAssetService assignedAssetService;

    @PostMapping("/assign")
    public AssignedAssetDTO assignAsset(@RequestBody AssignedAssetDTO dto) {
        return assignedAssetService.assignAsset(dto);
    }

    @GetMapping("/all")
    public List<AssignedAssetDTO> getAllAssignedAssets() {
        return assignedAssetService.getAllAssignedAssets();
    }

    @GetMapping("/{id}")
    public AssignedAssetDTO getById(@PathVariable int id) {
        return assignedAssetService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable int id) {
        return assignedAssetService.deleteAssignedAsset(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<AssignedAssetDTO> getByEmployee(@PathVariable int employeeId) {
        return assignedAssetService.getByEmployeeId(employeeId);
    }

    @GetMapping("/asset/{assetId}")
    public List<AssignedAssetDTO> getByAsset(@PathVariable int assetId) {
        return assignedAssetService.getByAssetId(assetId);
    }

    @GetMapping("/employee/{employeeId}/asset/{assetId}")
    public AssignedAssetDTO getByEmployeeAndAsset(@PathVariable int employeeId, @PathVariable int assetId) {
        return assignedAssetService.getByEmployeeAndAsset(employeeId, assetId);
    }
}
