package com.backend.assetmanagement.repository;
import com.backend.assetmanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e FROM Employee e WHERE e.email = ?1")
    Employee findByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE e.auth.email = ?1")
    Employee findByAuthEmail(String email);
}
