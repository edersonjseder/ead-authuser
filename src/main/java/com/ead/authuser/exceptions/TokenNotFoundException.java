package com.ead.authuser.exceptions;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(String value) {
        super(generateMessage(value));
    }

    private static String generateMessage(String value) {
        return "Token not found: " + value;
    }
}
