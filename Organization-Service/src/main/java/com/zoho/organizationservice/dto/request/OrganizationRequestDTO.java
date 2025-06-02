package com.zoho.organizationservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrganizationRequestDTO {
    @NotBlank(message = "Organization name is required")
    @Size(min = 3, message = "Organization name must be greater than 3 characters")
    private String organizationName;

    private String password;
    private Date createdAt;

    @Email(message = "Invalid email format")
    private String email;

    private String location;
    private BigInteger contactNO;
    private String webLink;
    private List<UUID> adminId;
    private boolean isApproved;

}
