package com.backend.assetmanagement.repository;

import com.backend.assetmanagement.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByAuthEmail(String email);
}