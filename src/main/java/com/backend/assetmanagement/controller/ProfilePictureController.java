package com.backend.assetmanagement.controller;

import com.backend.assetmanagement.model.ProfilePicture;
import com.backend.assetmanagement.service.ProfilePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/profile-pic")
@CrossOrigin(origins = "http://localhost:5173")
public class ProfilePictureController {

    @Autowired
    private ProfilePictureService profilePictureService;

    @PostMapping("/upload")
    public ResponseEntity<ProfilePicture> upload(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        ProfilePicture picture = profilePictureService.uploadProfilePic(principal.getName(), file);
        return ResponseEntity.ok(picture);
    }

    @GetMapping
    public ResponseEntity<ProfilePicture> getMyProfilePic(Principal principal) {
        ProfilePicture picture = profilePictureService.getProfilePic(principal.getName());
        return ResponseEntity.ok(picture);
    }
}
