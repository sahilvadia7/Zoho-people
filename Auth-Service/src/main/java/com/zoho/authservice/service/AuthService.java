package com.zoho.authservice.service;

import com.zoho.authservice.dto.request.LoginRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AuthService {
    ResponseEntity<?> login(LoginRequest request);
    ResponseEntity<?> validateToken(UUID token);
    ResponseEntity<?> getUserRole(UUID userId);
}
