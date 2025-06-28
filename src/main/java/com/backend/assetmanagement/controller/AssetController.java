package com.backend.assetmanagement.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.backend.assetmanagement.dto.AssetDTO;
import com.backend.assetmanagement.model.Asset;
import com.backend.assetmanagement.service.AssetService;

@RestController
@RequestMapping("/api/assets")
@CrossOrigin(origins = "http://localhost:5173")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @PostMapping("/add/{categoryId}")
    public Asset addAsset(@PathVariable int categoryId, @RequestBody Asset asset) {
        return assetService.addAsset(categoryId, asset);
    }

    @GetMapping("/category/{categoryId}")
    public List<AssetDTO> getAssetsByCategory(@PathVariable int categoryId) {
        return assetService.getAssetsByCategory(categoryId);
    }

    @GetMapping("/{id}")
    public AssetDTO getAssetById(@PathVariable int id) {
        return assetService.getAssetById(id);
    }

    @GetMapping("/all")
    public List<Asset> getAllAssets() {
        return assetService.getAllAssets();
    }

    @PutMapping("/update/{id}")
    public Asset updateAsset(@PathVariable int id, @RequestBody Asset asset) {
        return assetService.updateAsset(id, asset);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAsset(@PathVariable int id) {
        assetService.deleteAsset(id);
    }

    @GetMapping("/eligible")
    public List<Asset> getEligibleAssets(Principal principal) {
        return assetService.getEligibleAssetsForEmployee(principal.getName());
    }
    
    @GetMapping("/eligible-paged")
    public List<Asset> getEligibleAssetsPaged(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        return assetService.getEligibleAssetsPaged(principal.getName(), page, size);
    }
}