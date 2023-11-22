package com.ead.authuser.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordResponse {
    private String message;
    @JsonProperty(value = "current-password-date")
    private String currentPasswordDate;
    @JsonProperty(value = "last-updated-date")
    private String lastUpdatedDate;
}
