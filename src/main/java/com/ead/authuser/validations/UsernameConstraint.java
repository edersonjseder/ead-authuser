package com.ead.authuser.validations;

import com.ead.authuser.validations.impl.UsernameConstraintImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.ead.authuser.constants.UserMessagesConstants.USUARIO_USERNAME_INVALIDO_MENSAGEM;

@Documented
@Constraint(validatedBy = UsernameConstraintImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {
    // Default parameters of Validation Bean of Spring validation
    String message() default USUARIO_USERNAME_INVALIDO_MENSAGEM;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
