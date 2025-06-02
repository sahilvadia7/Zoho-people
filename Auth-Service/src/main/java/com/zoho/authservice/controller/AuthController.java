package com.zoho.authservice.controller;

import com.zoho.authservice.dto.request.PromoteRequest;
import com.zoho.authservice.dto.request.LoginRequest;
import com.zoho.authservice.dto.request.UserRegister;
import com.zoho.authservice.dto.response.ErrorResponse;
import com.zoho.authservice.service.AuthService;
import com.zoho.authservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth-API", description = "Authentication and Validation")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/employee/register")
    @Operation(summary = "Register user", description = "Registers a new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> registerUser(@RequestBody UserRegister userReq) {
        return userService.regUser(userReq);
    }

    @PostMapping("/employee/login")
    @Operation(summary = "Login user", description = "Logs in an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/validate-role/{userId}")
    @Operation(summary = "Get user role", description = "Validates and returns the user's role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> getRole(@PathVariable UUID userId) {
        return authService.getUserRole(userId);
    }

    @GetMapping("/fetchUser/{id}")
    @Operation(summary = "Fetch user", description = "Fetches user details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error retrieving user",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> fetchUser(@PathVariable("id") UUID userId) {
        return userService.fetchUser(userId);
    }

    @PostMapping("/validate-token/{id}")
    @Operation(summary = "Validate token", description = "Validates user's authentication token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token valid"),
            @ApiResponse(responseCode = "401", description = "Token invalid or expired",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> validateToken(@PathVariable("id") UUID userid) {
        return authService.validateToken(userid);
    }

    @PutMapping("/promote-role")
    @Operation(summary = "Promote user role", description = "Promotes user's role to higher access level")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User promoted successfully"),
            @ApiResponse(responseCode = "400", description = "Promotion failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error promoting user",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> promoteUserRole(@RequestBody PromoteRequest promoteRequest) {
        return userService.promoteUserRole(promoteRequest);
    }

    @GetMapping("/organization/validate-access/{userId}/{orgId}")
    @Operation(summary = "Validate user access", description = "Checks if user has access to the given organization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access valid"),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User or organization not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> checkUserAccessToOrg(@PathVariable UUID userId, @PathVariable UUID orgId) {
        return userService.validateUserOrganization(userId, orgId);
    }

}
