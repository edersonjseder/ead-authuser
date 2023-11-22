package com.ead.authuser.services;


import com.ead.authuser.dtos.ForgotPasswordDto;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.publishers.ForgotPasswordEventPublisher;
import com.ead.authuser.responses.EmailResponse;
import com.ead.authuser.utils.PasswordUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {
    private static final String OK = "Instructions sent to email {0}";

    @Value("${authuser.client.url}")
    private String clientUrl;

    private final PasswordUtils passwordUtils;
    private final TokenService tokenService;
    private final ForgotPasswordEventPublisher forgotPasswordEventPublisher;

    /**
     * This method gets the passwordResetToken object to make the proper link, so the
     * user can change his password
     *
     * @param request            to be used to generate the forgot password link
     * @param forgotPasswordDto The password object parameter
     */
    public EmailResponse generateLinkAndSendEmailToUser(HttpServletRequest request, ForgotPasswordDto forgotPasswordDto) {
        var passwordResetToken = tokenService.createPasswordResetTokenForEmail(forgotPasswordDto.getEmail());

        // Gets the userId & it gets the token from the object.
        var user = passwordResetToken.getUser();
        var token = passwordResetToken.getToken();

        var resetPasswordUrl = passwordUtils.createPasswordResetUrl(request, clientUrl, user.getId(), token);

        forgotPasswordEventPublisher.publishForgotPasswordEvent(passwordUtils.toForgotPasswordEventDto(user, resetPasswordUrl), ActionType.FORGOT);

        return EmailResponse.builder().emailSent(MessageFormat.format(OK, user.getEmail())).build();
    }
}
