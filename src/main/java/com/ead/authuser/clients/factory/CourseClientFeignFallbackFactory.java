package com.ead.authuser.clients.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CourseClientFeignFallbackFactory implements FallbackFactory<CourseClientFactory> {
    @Override
    public CourseClientFactory create(Throwable cause) {
        log.error("Inside circuitbreaker fallback, cause - {}", cause.getMessage());
        return new CourseClientFactory();
    }
}
