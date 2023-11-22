package com.ead.authuser.controllers;

import com.ead.authuser.dtos.InstructorDto;
import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.enums.Roles;
import com.ead.authuser.security.annotation.HasProperAuthority;
import com.ead.authuser.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructors")
@RequiredArgsConstructor
public class InstructorUserController {
    private final UserService userService;

    @HasProperAuthority(authorities = {Roles.ROLE_ADMIN})
    @PutMapping(value = "/subscribe")
    public ResponseEntity<UserDto> subscribeInstructor(@RequestBody @Valid InstructorDto instructorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.subscribeInstructor(instructorDto, ActionType.UPDATE));
    }
}
