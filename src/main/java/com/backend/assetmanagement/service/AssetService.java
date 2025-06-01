package com.backend.assetmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Asset;
import com.backend.assetmanagement.model.AssetCategory;
import com.backend.assetmanagement.repository.AssetCategoryRepository;
import com.backend.assetmanagement.repository.AssetRepository;

@Service
public class AssetService {

    private final AssetRepository assetRepo;
    private final AssetCategoryRepository categoryRepo;

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

    public List<Asset> getAssetsByCategory(int categoryId) {
        categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset Category not found with ID: " + categoryId));
        return assetRepo.findByCategoryId(categoryId);
    }

    public Asset getAssetById(int id) {
        return assetRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: " + id));
    }

    public List<Asset> getAllAssets() {
        return assetRepo.findAll();
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
}