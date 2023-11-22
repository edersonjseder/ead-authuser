package com.ead.authuser.errorhandler;

import com.ead.authuser.exceptions.BadRequestException;
import com.ead.authuser.exceptions.CourseNotFoundException;
import com.ead.authuser.exceptions.CourseServiceNotAvailableException;
import feign.Response;
import feign.codec.ErrorDecoder;

import static com.ead.authuser.constants.UserMessagesConstants.COURSE_NOT_FOUND_MENSAGEM;
import static com.ead.authuser.constants.UserMessagesConstants.COURSE_SERVICE_MENSAGEM;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 400 -> new BadRequestException();
            case 404 -> new CourseNotFoundException(COURSE_NOT_FOUND_MENSAGEM);
            case 503 -> new CourseServiceNotAvailableException(COURSE_SERVICE_MENSAGEM);
            default -> new Exception("Exception while getting details");
        };
    }
}
