// File: src/main/java/com/example/asyncnotemanagerapi/dto/LoginRequest.java
package com.example.asyncnotemanagerapi.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password
) {}
