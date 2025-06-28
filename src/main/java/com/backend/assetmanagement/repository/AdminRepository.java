package com.backend.assetmanagement.repository;

import com.backend.assetmanagement.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("SELECT a FROM Admin a WHERE a.auth.email = ?1")
    Admin findByAuthEmail(String email);
}
