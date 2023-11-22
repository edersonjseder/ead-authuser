package com.ead.authuser.utils;

import com.ead.authuser.dtos.ForgotPasswordEventDto;
import com.ead.authuser.dtos.ChangePasswordEventDto;
import com.ead.authuser.models.User;
import com.ead.authuser.responses.PasswordResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.ead.authuser.constants.UserMessagesConstants.USUARIO_SENHA_SUCESSO_MENSAGEM;

@Component
@RequiredArgsConstructor
public class PasswordUtils {
    @Value("${mail.smtp.username}")
    private String emailFrom;
    private final DateUtils dateUtils;
    private static final String CHANGE_PASSWORD_PATH = "/password/change";

    /**
     * Builds and returns the URL to reset the user password and send by email.
     *
     * @param request   The Http Servlet Request.
     * @param clientUrl the frontend url
     * @param userId    The user id.
     * @param token     The token
     * @return the URL to reset the user password.
     */
    public String createPasswordResetUrl(HttpServletRequest request, String clientUrl, UUID userId, String token) {

        return request.getScheme() +
                "://" +
                clientUrl +
                CHANGE_PASSWORD_PATH +
                "?token=" +
                token +
                "&userId=" +
                userId;
    }

    public ChangePasswordEventDto toChangePasswordEventDto(User user) {
        return ChangePasswordEventDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .message(USUARIO_SENHA_SUCESSO_MENSAGEM)
                .build();
    }

    public ForgotPasswordEventDto toForgotPasswordEventDto(User user, String link) {
        return ForgotPasswordEventDto.builder()
                .emailFrom(emailFrom)
                .emailTo(user.getEmail())
                .fullName(user.getFullName())
                .link(link)
                .build();
    }

    public PasswordResponse toPasswordResponse(User user) {
        return PasswordResponse.builder()
                .message(USUARIO_SENHA_SUCESSO_MENSAGEM)
                .currentPasswordDate(dateUtils.parseDate(user.getCurrentPasswordDate()))
                .lastUpdatedDate(dateUtils.parseDate(user.getLastUpdateDate()))
                .build();
    }
}
