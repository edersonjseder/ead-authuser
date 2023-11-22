package com.ead.authuser.controllers;

import com.ead.authuser.dtos.ResetPasswordDto;
import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.responses.PasswordResponse;
import com.ead.authuser.services.ChangeUserPasswordService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class ChangeUserPasswordController {
    private final ChangeUserPasswordService changeUserPasswordService;

    @PutMapping(value = "/change")
    public ResponseEntity<PasswordResponse> updatePassword(@RequestBody ResetPasswordDto resetPassword) {
        return ResponseEntity.status(HttpStatus.OK).body(changeUserPasswordService.updateUserPassword(resetPassword));
    }
}
