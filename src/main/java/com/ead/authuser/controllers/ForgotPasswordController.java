package com.ead.authuser.controllers;


import com.ead.authuser.dtos.ForgotPasswordDto;
import com.ead.authuser.responses.EmailResponse;
import com.ead.authuser.services.ForgotPasswordService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/password")
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping(path = "/forgot")
    public ResponseEntity<EmailResponse> forgotPasswordPost(HttpServletRequest request, @RequestBody ForgotPasswordDto forgotDto) {
        var value = forgotPasswordService.generateLinkAndSendEmailToUser(request, forgotDto);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }
}
