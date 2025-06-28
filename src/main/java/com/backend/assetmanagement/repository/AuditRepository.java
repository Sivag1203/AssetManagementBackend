package com.backend.assetmanagement.repository;

import com.backend.assetmanagement.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuditRepository extends JpaRepository<Audit, Integer> {

    @Query("SELECT a FROM Audit a WHERE a.status = 'pending'")
    List<Audit> findPendingAudits();

    @Query("SELECT a FROM Audit a WHERE a.employee.id = ?1")
    List<Audit> findAuditsByEmployeeId(int employeeId);

    @Query("SELECT a FROM Audit a WHERE a.asset.id = ?1 AND a.employee.id = ?2")
    Optional<Audit> findByAssetIdAndEmployeeId(int assetId, int employeeId);
}
