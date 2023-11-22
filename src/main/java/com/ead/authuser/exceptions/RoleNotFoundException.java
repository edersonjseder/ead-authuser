package com.ead.authuser.exceptions;

import static com.ead.authuser.constants.RoleMessagesConstants.ROLE_NOT_FOUND_MESSAGE;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super(ROLE_NOT_FOUND_MESSAGE);
    }
}
