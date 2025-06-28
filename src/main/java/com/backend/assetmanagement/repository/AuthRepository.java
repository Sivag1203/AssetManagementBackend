package com.backend.assetmanagement.repository;

import com.backend.assetmanagement.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Integer> {

    @Query("SELECT a FROM Auth a WHERE a.email = ?1")
    Auth findByEmail(String email);
}
