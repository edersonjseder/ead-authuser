package com.ead.authuser.clients;

import com.ead.authuser.clients.factory.CourseClientFeignFallbackFactory;
import com.ead.authuser.clients.params.CourseClientParams;
import com.ead.authuser.configs.FeignConfig;
import com.ead.authuser.dtos.CourseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${ead.course.url}", configuration = FeignConfig.class, fallbackFactory = CourseClientFeignFallbackFactory.class)
//@FeignClient(name = "${ead.course.url}", configuration = FeignConfig.class)
public interface CourseClientFeign {
    @GetMapping(path = "/all")
    @CircuitBreaker(name = "circuit-get-all-courses-by-user")
    Page<CourseDto> getAllCoursesByUser(@SpringQueryMap CourseClientParams params, @RequestHeader("Authorization") String header);
}
