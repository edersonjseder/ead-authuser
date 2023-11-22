package com.ead.authuser.controllers;

import com.ead.authuser.dtos.SignInDto;
import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.responses.TokenResponse;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping(value = "/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody @Validated(UserDto.UserView.RegistrationPost.class) @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {
        log.debug("POST signUp userDto received {} ", userDto.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDto, ActionType.CREATE));
    }

    @PostMapping(value = "/signIn")
    public ResponseEntity<TokenResponse> authenticateUser(@Valid @RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(userService.signIn(signInDto));
    }
}
