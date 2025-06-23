package com.backend.assetmanagement.repository;

import com.backend.assetmanagement.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Integer> {

    @Query("SELECT s FROM ServiceRequest s WHERE s.employee.id = :employeeId")
    List<ServiceRequest> findByEmployeeId(int employeeId);
    
    @Query("SELECT sr.status, COUNT(sr) FROM ServiceRequest sr GROUP BY sr.status")
    List<Object[]> countGroupedByStatus();
}
