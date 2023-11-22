package com.ead.authuser.clients.factory;

import com.ead.authuser.clients.CourseClientFeign;
import com.ead.authuser.clients.params.CourseClientParams;
import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.exceptions.CourseServiceNotAvailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.util.UUID;

import static com.ead.authuser.constants.UserMessagesConstants.COURSE_FEIGN_SERVICE_MENSAGEM;

@Slf4j
public class CourseClientFactory implements CourseClientFeign {
    @Override
    public Page<CourseDto> getAllCoursesByUser(CourseClientParams params, String header) {
        log.error("Inside circuitbreaker fallback, getAllCoursesByUser - {}", params.getUserId());
        callCourseClientFallback(params.getUserId());
        return null;
    }
    private void callCourseClientFallback(UUID userId) {
        throw new CourseServiceNotAvailableException(COURSE_FEIGN_SERVICE_MENSAGEM + userId);
    }
}
