package com.zoho.authservice.repository;

import com.zoho.authservice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByName(String name);
    Optional<Users> findByEmail(String email);
    List<Users> findByOrganizationId(UUID organizationId);
}
