// File: src/main/java/com/example/asyncnotemanagerapi/dto/RegisterRequest.java
package com.example.asyncnotemanagerapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank @Size(min = 6) String password
) {}
