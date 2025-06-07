package com.backend.assetmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.assetmanagement.dto.AssetDTO;
import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Asset;
import com.backend.assetmanagement.model.AssetCategory;
import com.backend.assetmanagement.model.Employee;
import com.backend.assetmanagement.repository.AssetCategoryRepository;
import com.backend.assetmanagement.repository.AssetRepository;
import com.backend.assetmanagement.repository.EmployeeRepository;

@Service
public class AssetService {

    private final AssetRepository assetRepo;
    private final AssetCategoryRepository categoryRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    public AssetService(AssetRepository assetRepo, AssetCategoryRepository categoryRepo) {
        this.assetRepo = assetRepo;
        this.categoryRepo = categoryRepo;
    }

    public Asset addAsset(int categoryId, Asset asset) {
        AssetCategory category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset Category not found with ID: " + categoryId));
        asset.setCategory(category);
        return assetRepo.save(asset);
    }

    public List<AssetDTO> getAssetsByCategory(int categoryId) {
        categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset Category not found with ID: " + categoryId));
        List<Asset> assets = assetRepo.findByCategoryId(categoryId);
        return AssetDTO.convertToDTOList(assets);
    }

    public AssetDTO getAssetById(int id) {
        Asset asset = assetRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: " + id));
        return new AssetDTO(asset.getId(), asset.getSerialNumber(), asset.getSpecs(), asset.getStatus());
    }

    public List<AssetDTO> getAllAssets() {
        List<Asset> assets = assetRepo.findAll();
        return AssetDTO.convertToDTOList(assets);
    }

    public Asset updateAsset(int assetId, Asset updatedAsset) {
        Asset asset = assetRepo.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: " + assetId));

        asset.setSerialNumber(updatedAsset.getSerialNumber());
        asset.setSpecs(updatedAsset.getSpecs());
        asset.setStatus(updatedAsset.getStatus());
        return assetRepo.save(asset);
    }

    public void deleteAsset(int id) {
        Asset asset = assetRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: " + id));
        assetRepo.delete(asset);
    }

    public List<AssetDTO> getEligibleAssetsForEmployee(String email) {
        Employee employee = employeeRepo.findByEmail(email);
        if (employee == null) return List.of(); 
        List<Asset> assets = assetRepo.findEligibleAssetsForLevel(employee.getLevel().name());
        return AssetDTO.convertToDTOList(assets);
    }
}
