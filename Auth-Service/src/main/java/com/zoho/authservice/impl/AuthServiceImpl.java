package com.zoho.authservice.impl;

import com.zoho.authservice.dto.request.LoginRequest;
import com.zoho.authservice.model.AuthToken;
import com.zoho.authservice.model.Users;
import com.zoho.authservice.repository.AuthRepository;
import com.zoho.authservice.repository.UserRepository;
import com.zoho.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.zoho.authservice.constrain.GlobalConstrain.*;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepo;
    private final UserRepository userRepo;

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public ResponseEntity<?> login(LoginRequest request) {

        Optional<Users> existUserOptional = userRepo.findByEmail(request.getEmail());

        if (existUserOptional.isEmpty()) {
            return new ResponseEntity<>(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        Users existingUser = existUserOptional.get();

        boolean passwordMatches = passwordEncoder().matches(request.getPassword(), existingUser.getPassword());

        if (!passwordMatches) {
            return new ResponseEntity<>(BAD_CREDENTIAL, HttpStatus.UNAUTHORIZED);
        }

        Optional<AuthToken> authEntryOptional = authRepo.findByUserId(existingUser.getUserID());

        if (authEntryOptional.isPresent()) {
            AuthToken existingAuthToken = authEntryOptional.get();

            if (existingAuthToken.getExpiresAt() != null && existingAuthToken.getExpiresAt().isAfter(LocalDate.now())) {
                return new ResponseEntity<>("User already logged in. Existing token: " + existingAuthToken.getToken().toString(), HttpStatus.OK);
            } else {
                authRepo.delete(existingAuthToken);
            }
        }

        UUID newAuthTokenUUID = UUID.randomUUID();

        LocalDate today = LocalDate.now();
        LocalDate expiryDate = today.plusDays(30);

        AuthToken newToken = AuthToken.builder()
                .token(newAuthTokenUUID)
                .userId(existingUser.getUserID())
                .createdAt(new Date())
                .expiresAt(expiryDate)
                .build();

        authRepo.save(newToken);

        return new ResponseEntity<>("User authorized. New token: " + newToken.getToken().toString(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> validateToken(UUID userId) {

        Optional<AuthToken> tokenObjOptional = authRepo.findByUserId(userId);

        if (tokenObjOptional.isEmpty()) {
            return new ResponseEntity<>("Token not found for this user.", HttpStatus.NOT_FOUND);
        }

        AuthToken tokenObj = tokenObjOptional.get();

        LocalDate expirationDate = tokenObj.getExpiresAt();
        LocalDate currentTime = LocalDate.now();

        boolean isTokenExpired = expirationDate.isBefore(currentTime);

        if (isTokenExpired) {
            authRepo.delete(tokenObj);
            return new ResponseEntity<>("Token has expired.", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Token is valid.", HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getUserRole(UUID userId) {
        Optional<Users> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            return new ResponseEntity<>(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user.get().getRole());
    }

}

