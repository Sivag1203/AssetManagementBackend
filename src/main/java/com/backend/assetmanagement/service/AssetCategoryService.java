package com.backend.assetmanagement.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.AssetCategory;
import com.backend.assetmanagement.repository.AssetCategoryRepository;

@Service
public class AssetCategoryService {

    private final AssetCategoryRepository assetCategoryRepository;

    public AssetCategoryService(AssetCategoryRepository assetCategoryRepository) {
        this.assetCategoryRepository = assetCategoryRepository;
    }

    public AssetCategory addCategory(AssetCategory category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty.");
        }
        return assetCategoryRepository.save(category);
    }

    public List<AssetCategory> getAllCategories() {
        return assetCategoryRepository.findAll();
    }

    public AssetCategory getCategoryById(int id) {
        return assetCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset Category not found with id: " + id));
    }

    public AssetCategory updateCategory(int id, AssetCategory updatedCategory) {
        if (updatedCategory.getName() == null || updatedCategory.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty.");
        }

        AssetCategory existing = getCategoryById(id);
        existing.setName(updatedCategory.getName());
        return assetCategoryRepository.save(existing);
    }

    public void deleteCategory(int id) {
        AssetCategory existing = getCategoryById(id);
        assetCategoryRepository.delete(existing);
    }
}
