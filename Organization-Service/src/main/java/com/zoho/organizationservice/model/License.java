package com.zoho.organizationservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "License", description = "Licence Model")
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    @JsonBackReference
    private Organization organization;

    @Column(name = "requested_count", nullable = false)
    private int requestedCount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "requested_at", nullable = false, updatable = false)
    private Date requestedAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "processed_at")
    private Date processedAt;

    @Column(name = "processed_by")
    private UUID processedBy;
}