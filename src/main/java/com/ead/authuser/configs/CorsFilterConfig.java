package com.ead.authuser.configs;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilterConfig implements Filter {

    public static final String[] ALLOWED_ORIGINS = {
            "http://localhost:4200",
            "http://localhost:5000"
    };
    public static final String[] METHODS_LIST = {"GET", "POST", "PUT", "PATCH", "DELETE"};
    public static final String[] HEADERS = {"accept", "apikey", "Authorization"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", Arrays.stream(ALLOWED_ORIGINS).map(Object::toString).collect(Collectors.joining(",")));
        response.setHeader("Access-Control-Allow-Methods", Arrays.stream(METHODS_LIST).map(Object::toString).collect(Collectors.joining(",")));
        response.setHeader("Access-Control-Allow-Headers", Arrays.stream(HEADERS).map(Object::toString).collect(Collectors.joining(",")));
        response.setHeader("Access-Control-Max-Age", "3600");

        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(((HttpServletRequest) servletRequest).getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
