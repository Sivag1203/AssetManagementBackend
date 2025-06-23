package com.backend.assetmanagement.security;

import com.backend.assetmanagement.model.Auth;
import com.backend.assetmanagement.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Auth auth = authRepository.findByEmail(email);
        if (auth == null)
            throw new UsernameNotFoundException("User not found with email: " + email);

        return new User(
                auth.getEmail(),
                auth.getPassword(),
                List.of(new SimpleGrantedAuthority(auth.getRole())) 
        );
    }
}
