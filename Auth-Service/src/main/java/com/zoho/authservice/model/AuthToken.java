package com.zoho.authservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID token;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "expires_at")
    private LocalDate expiresAt;
}

