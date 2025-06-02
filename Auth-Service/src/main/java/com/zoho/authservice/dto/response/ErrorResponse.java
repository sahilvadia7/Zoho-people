package com.zoho.authservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Standard error response format")
public class ErrorResponse {
    @Schema(description = "Error code", example = "NOT_FOUND")
    private String code;

    @Schema(description = "Detailed error message", example = "User not found with given ID")
    private String message;
}
