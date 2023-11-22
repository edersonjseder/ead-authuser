package com.ead.authuser.exceptions;

import java.util.UUID;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String value) {
        super(value);
    }
    public CourseNotFoundException(UUID value) {
        super(generateMessage(value));
    }
    private static String generateMessage(UUID value) {
        return "No user related to the course with value: " + value;
    }
}
