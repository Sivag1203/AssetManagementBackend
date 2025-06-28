package com.backend.assetmanagement.repository;

import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.model.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Integer> {

    @Query("SELECT p FROM ProfilePicture p WHERE p.auth = ?1")
    ProfilePicture findByAuth(Auth auth);
}
