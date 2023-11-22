package com.ead.authuser.dtos;

import com.ead.authuser.models.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class TokenDto {
    private UUID id;
    private String token;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
