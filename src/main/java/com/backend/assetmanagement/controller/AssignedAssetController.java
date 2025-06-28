package com.backend.assetmanagement.controller;

import com.backend.assetmanagement.dto.AssignedAssetDTO;
import com.backend.assetmanagement.model.AssignedAsset;
import com.backend.assetmanagement.service.AssignedAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assigned-assets")
@CrossOrigin(origins = "http://localhost:5173")
public class AssignedAssetController {

    @Autowired
    private AssignedAssetService assignedAssetService;

    @PostMapping("/assign")
    public AssignedAsset assignAsset(@RequestBody AssignedAsset dto) {
        return assignedAssetService.assignAsset(dto);
    }

    @GetMapping("/all")
    public List<AssignedAsset> getAllAssignedAssets() {
        return assignedAssetService.getAllAssignedAssets();
    }

    @GetMapping("/{id}")
    public AssignedAsset getById(@PathVariable int id) {
        return assignedAssetService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable int id) {
        return assignedAssetService.deleteAssignedAsset(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<AssignedAsset> getByEmployee(@PathVariable int employeeId) {
        return assignedAssetService.getByEmployeeId(employeeId);
    }

    @GetMapping("/asset/{assetId}")
    public List<AssignedAsset> getByAsset(@PathVariable int assetId) {
        return assignedAssetService.getByAssetId(assetId);
    }

    @GetMapping("/employee/{employeeId}/asset/{assetId}")
    public AssignedAsset getByEmployeeAndAsset(@PathVariable int employeeId, @PathVariable int assetId) {
        return assignedAssetService.getByEmployeeAndAsset(employeeId, assetId);
    }
}
