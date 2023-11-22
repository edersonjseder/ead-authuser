package com.ead.authuser.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordEventDto {
    private String username;
    private String email;
    private String fullName;
    private String message;
    private String actionType;
}
