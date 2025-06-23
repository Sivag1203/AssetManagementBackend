package com.backend.assetmanagement.service;

import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.model.ProfilePicture;
import com.backend.assetmanagement.repository.AuthRepository;
import com.backend.assetmanagement.repository.ProfilePictureRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
public class ProfilePictureService {

    private final ProfilePictureRepository pictureRepository;
    private final AuthRepository authRepository;

    public ProfilePictureService(ProfilePictureRepository pictureRepository, AuthRepository authRepository) {
        this.pictureRepository = pictureRepository;
        this.authRepository = authRepository;
    }

    public ProfilePicture uploadProfilePic(String email, MultipartFile file) throws IOException {
        Auth auth = authRepository.findByEmail(email);
        if (auth == null) throw new RuntimeException("User not found");

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

        if (!List.of("jpg", "jpeg", "png", "gif", "svg").contains(extension.toLowerCase())) {
            throw new RuntimeException("Invalid file extension");
        }

        long sizeKB = file.getSize() / 1024;
        if (sizeKB > 3000) {
            throw new RuntimeException("Image size too large");
        }

        String uploadFolder = "C:/Users/sivna/Downloads/AssetManagement/assetmanagement-frontend/assetmanagement/public/images";
        Files.createDirectories(Path.of(uploadFolder));
        Path filePath = Paths.get(uploadFolder, originalFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        ProfilePicture pic = pictureRepository.findByAuth(auth);
        if (pic == null) {
            pic = new ProfilePicture();
            pic.setAuth(auth);
        }
        pic.setFileName(originalFileName);

        return pictureRepository.save(pic);
    }

    public ProfilePicture getProfilePic(String email) {
        Auth auth = authRepository.findByEmail(email);
        return pictureRepository.findByAuth(auth);
    }
}
