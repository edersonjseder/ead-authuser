package com.ead.authuser.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TB_TOKENS")
public class Token implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_TOKEN_LENGTH_IN_MINUTES = 120;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String token;
    private UUID userId;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    /**
     * Full constructor.
     *
     * @param token The user token, it must be not null.
     * @param user The user for which the token should be created. It must not be null.
     * @param creationDateTime The date time when this request was created. It must not be null.
     * @param expirationInMinutes The length, in minutes, for which this token will be valid. if zero
     *                            it will be assigned a default value of 120 (2 hours)
     * @throws IllegalArgumentException If the token, user or creation date is null.
     *
     */
    public Token(String token, User user, LocalDateTime creationDateTime, int expirationInMinutes) {

        if ((token == null) || (null == user) || (null == creationDateTime)) {
            throw new IllegalArgumentException("Token, User and creation date time can't be null");
        }

        if (expirationInMinutes == 0) {
            expirationInMinutes = DEFAULT_TOKEN_LENGTH_IN_MINUTES;
        }

        this.token = token;
        this.userId = user.getId();
        this.expiresAt = creationDateTime.plusMinutes(expirationInMinutes);
        this.createdAt = creationDateTime;
    }
}
