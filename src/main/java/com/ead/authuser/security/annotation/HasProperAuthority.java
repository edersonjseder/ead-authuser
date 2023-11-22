package com.ead.authuser.security.annotation;

import com.ead.authuser.enums.Roles;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface HasProperAuthority {
    Roles[] authorities();
}
