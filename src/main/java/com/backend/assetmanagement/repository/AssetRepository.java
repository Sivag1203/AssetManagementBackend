package com.backend.assetmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.assetmanagement.model.Asset;

public interface AssetRepository extends JpaRepository<Asset, Integer> {

    @Query("SELECT a FROM Asset a WHERE a.category.id = ?1")
    List<Asset> findByCategoryId(int categoryId);
}