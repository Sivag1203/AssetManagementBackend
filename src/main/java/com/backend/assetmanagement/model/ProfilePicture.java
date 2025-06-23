package com.backend.assetmanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile_pictures")
public class ProfilePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fileName;

    private LocalDateTime uploadedAt = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "auth_id", referencedColumnName = "id")
    private Auth auth;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }

    public Auth getAuth() { return auth; }
    public void setAuth(Auth auth) { this.auth = auth; }
}
