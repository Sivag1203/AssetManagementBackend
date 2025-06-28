package com.backend.assetmanagement.repository;

import com.backend.assetmanagement.model.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Integer> {

    @Query("SELECT r FROM ReturnRequest r WHERE r.employee.id = ?1")
    List<ReturnRequest> findByEmployeeId(int employeeId);

    @Query("SELECT rr.status, COUNT(rr) FROM ReturnRequest rr GROUP BY rr.status")
    List<Object[]> countGroupedByStatus();
}
