package com.zoho.authservice.repository;

import com.zoho.authservice.model.AuthToken;
import com.zoho.authservice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<AuthToken,UUID> {
    Optional<AuthToken> findByUserId(UUID token);
}
