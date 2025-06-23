package com.backend.assetmanagement.repository;

import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.model.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Integer> {
    ProfilePicture findByAuth(Auth auth);
}
