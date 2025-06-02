package com.zoho.organizationservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "OrganizationModel", description = "Organization Model")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "organization_id")
    private UUID organizationID;

    @NotBlank(message = "Organization name cannot be blank")
    @Size(min = 3, message = "Organization name must be greater than 3 characters")
    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    @Email(message = "Invalid email format")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "org_password", nullable = false)
    private String password;

    @Column(name = "location")
    private String location;

    @Column(name = "contact_no")
    private BigInteger contactNO;

    @Column(name = "web_link")
    private String webLink;

    @Column(name = "auth_key", nullable = false, unique = true)
    private String authKey;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved = false;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ElementCollection
    @CollectionTable(name = "organization_admins", joinColumns = @JoinColumn(name = "organization_id"))
    @Column(name = "admin_id")
    private List<UUID> adminId;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<License> licenses = new ArrayList<>();

}
