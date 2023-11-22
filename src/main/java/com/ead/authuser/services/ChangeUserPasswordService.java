package com.ead.authuser.services;

import com.ead.authuser.dtos.ResetPasswordDto;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.exceptions.PasswordException;
import com.ead.authuser.exceptions.PasswordsNotMatchException;
import com.ead.authuser.exceptions.UserNotFoundException;
import com.ead.authuser.publishers.ChangePasswordEventPublisher;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.responses.PasswordResponse;
import com.ead.authuser.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ChangeUserPasswordService {
    private final PasswordUtils passwordUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ChangePasswordEventPublisher changePasswordEventPublisher;

    @Transactional
    public PasswordResponse updateUserPassword(ResetPasswordDto resetPasswordDto) {
        var user = userRepository.findUserByEmail(resetPasswordDto.getEmail()).orElseThrow(() -> new UserNotFoundException(resetPasswordDto.getEmail()));

        if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword())) {
            throw new PasswordsNotMatchException();
        }

        if (!passwordEncoder.matches(resetPasswordDto.getOldPassword(), user.getPassword())) {
            throw new PasswordException();
        }

        var hashedPassword = passwordEncoder.encode(resetPasswordDto.getPassword());

        user.setPassword(hashedPassword);
        user.setCurrentPasswordDate(LocalDateTime.now(ZoneId.of("UTC")));
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        changePasswordEventPublisher.publishChangePasswordEvent(passwordUtils.toChangePasswordEventDto(user), ActionType.SEND);

        return passwordUtils.toPasswordResponse(user);
    }
}
