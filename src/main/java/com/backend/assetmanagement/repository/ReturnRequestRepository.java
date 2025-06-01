package com.backend.assetmanagement.repository;

import com.backend.assetmanagement.model.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Integer> {

    @Query("SELECT r FROM ReturnRequest r WHERE r.employee.id = :employeeId")
    List<ReturnRequest> findByEmployeeId(int employeeId);
}
	