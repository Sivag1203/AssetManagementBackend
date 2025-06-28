package com.backend.assetmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.assetmanagement.model.AssetCategory;

public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Integer> {
	@Query("SELECT c FROM AssetCategory c WHERE c.name = ?1")
    AssetCategory findByName(String name);
}
