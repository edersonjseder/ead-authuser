package com.ead.authuser.exceptions;

import static com.ead.authuser.constants.UserMessagesConstants.USUARIO_SENHA_MENSAGEM;

public class PasswordsNotMatchException extends RuntimeException {

    public PasswordsNotMatchException() {
        super(USUARIO_SENHA_MENSAGEM);
    }
}
