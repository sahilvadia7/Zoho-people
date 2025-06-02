package com.zoho.authservice.service;

import com.zoho.authservice.dto.request.PromoteRequest;
import com.zoho.authservice.dto.request.UserRegister;
import com.zoho.authservice.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    ResponseEntity<?> regUser(UserRegister userReq);
    ResponseEntity<?> promoteUserRole(PromoteRequest promoteRequest);
//    ResponseEntity<?> getUsersByOrganization(UUID userId);
    ResponseEntity<?> validateUserOrganization(UUID userId, UUID orgId);
    ResponseEntity<?> fetchUser(UUID userId);
}
