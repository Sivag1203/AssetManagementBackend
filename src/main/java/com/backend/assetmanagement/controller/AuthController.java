package com.backend.assetmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/add")
    public Auth addAuth(@RequestBody Auth auth) {
        return authService.addAuth(auth);
    }

    @GetMapping("/all")
    public List<Auth> getAllAuths() {
        return authService.getAllAuths();
    }

    @GetMapping("/{id}")
    public Auth getAuthById(@PathVariable int id) {
        return authService.getAuthById(id);
    }

    @PutMapping("/update/{id}")
    public Auth updateAuth(@PathVariable int id, @RequestBody Auth updatedAuth) {
        return authService.updateAuth(id, updatedAuth);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAuth(@PathVariable int id) {
        authService.deleteAuth(id);
        return "Auth deleted successfully with id: " + id;
    }
}
