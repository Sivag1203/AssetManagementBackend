package com.backend.assetmanagement.repository;

import com.backend.assetmanagement.model.AssetRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssetRequestRepository extends JpaRepository<AssetRequest, Integer> {

    @Query("SELECT ar FROM AssetRequest ar WHERE ar.employee.id = ?1")
    List<AssetRequest> findByEmployeeId(int employeeId);

    @Query("SELECT ar.status, COUNT(ar) FROM AssetRequest ar GROUP BY ar.status")
    List<Object[]> countGroupedByStatus();
}
