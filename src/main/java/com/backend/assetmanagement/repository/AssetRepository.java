package com.backend.assetmanagement.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.assetmanagement.enums.Level;
import com.backend.assetmanagement.model.Asset;

public interface AssetRepository extends JpaRepository<Asset, Integer> {

    @Query("SELECT a FROM Asset a WHERE a.category.id = ?1")
    List<Asset> findByCategoryId(int categoryId);
    
    @Query("SELECT a FROM Asset a WHERE (a.eligibilityLevel = 'L1' OR (a.eligibilityLevel = 'L2' AND ?1 IN ('L2', 'L3')) OR (a.eligibilityLevel = 'L3' AND ?1 = 'L3'))")
    	List<Asset> findEligibleAssetsForLevel(String level);

    Page<Asset> findByEligibilityLevelLessThanEqual(Level level, Pageable pageable);
}