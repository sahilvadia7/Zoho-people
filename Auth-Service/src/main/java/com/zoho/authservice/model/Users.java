package com.zoho.authservice.model;

import com.zoho.authservice.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "register_user")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userID;

    @NotBlank(message = "user name not be null")
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Email(message ="email is not in proper formate")
    private String email;

    @Size(min = 4,message ="minimum size more-than 4")
    private String password;

    @Column(name = "organization_id")
    private UUID organizationId;
}
