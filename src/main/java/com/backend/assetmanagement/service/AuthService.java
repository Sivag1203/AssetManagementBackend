package com.backend.assetmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.assetmanagement.exception.ResourceNotFoundException;
import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.repository.AuthRepository;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    public Auth addAuth(Auth auth) {
        return authRepository.save(auth);
    }

    public List<Auth> getAllAuths() {
        return authRepository.findAll();
    }

    public Auth getAuthById(int id) {
        return authRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auth not found with id: " + id));
    }

    public Auth updateAuth(int id, Auth updatedAuth) {
        Auth existingAuth = authRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auth not found with id: " + id));

        existingAuth.setEmail(updatedAuth.getEmail());
        existingAuth.setPassword(updatedAuth.getPassword());
        existingAuth.setRole(updatedAuth.getRole());

        return authRepository.save(existingAuth);
    }

    public void deleteAuth(int id) {
        Auth auth = authRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auth not found with id: " + id));

        authRepository.delete(auth);
    }
}