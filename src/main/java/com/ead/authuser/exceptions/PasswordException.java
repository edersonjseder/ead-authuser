package com.ead.authuser.exceptions;

import static com.ead.authuser.constants.UserMessagesConstants.USUARIO_SENHA_INVALIDA_MENSAGEM;

public class PasswordException extends RuntimeException {

    public PasswordException() {
        super(USUARIO_SENHA_INVALIDA_MENSAGEM);
    }
}
