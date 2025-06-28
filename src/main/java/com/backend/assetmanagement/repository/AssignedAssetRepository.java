package com.backend.assetmanagement.repository;

import com.backend.assetmanagement.model.AssignedAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssignedAssetRepository extends JpaRepository<AssignedAsset, Integer> {

    @Query("SELECT aa FROM AssignedAsset aa WHERE aa.employee.id = ?1")
    List<AssignedAsset> findByEmployeeId(int employeeId);

    @Query("SELECT aa FROM AssignedAsset aa WHERE aa.asset.id = ?1")
    List<AssignedAsset> findByAssetId(int assetId);

    @Query("SELECT aa FROM AssignedAsset aa WHERE aa.employee.id = ?1 AND aa.asset.id = ?2")
    AssignedAsset findByEmployeeAndAsset(int employeeId, int assetId);
}
