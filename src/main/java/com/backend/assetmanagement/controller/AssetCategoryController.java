package com.backend.assetmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.backend.assetmanagement.model.AssetCategory;
import com.backend.assetmanagement.service.AssetCategoryService;

@RestController
@RequestMapping("/api/asset-category")
@CrossOrigin(origins = "http://localhost:5173/")
public class AssetCategoryController {

    @Autowired
    private AssetCategoryService assetCategoryService;

    @PostMapping("/add")
    public AssetCategory addCategory(@RequestBody AssetCategory category) {
        return assetCategoryService.addCategory(category);
    }

    @GetMapping("/all")
    public List<AssetCategory> getAllCategories() {
        return assetCategoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public AssetCategory getCategoryById(@PathVariable int id) {
        return assetCategoryService.getCategoryById(id);
    }

    @PutMapping("/update/{id}")
    public AssetCategory updateCategory(@PathVariable int id, @RequestBody AssetCategory category) {
        return assetCategoryService.updateCategory(id, category);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        assetCategoryService.deleteCategory(id);
        return "Asset Category deleted successfully!";
    }
}