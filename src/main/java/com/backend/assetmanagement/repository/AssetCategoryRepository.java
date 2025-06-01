package com.backend.assetmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.assetmanagement.model.AssetCategory;

public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Integer> {
}
