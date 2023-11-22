package com.ead.authuser.exceptionhandler;


import com.ead.authuser.exceptions.*;
import com.ead.authuser.responses.ErrorResponse;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST, ex.getLocalizedMessage());
        return buildResponseEntity(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        var message = errors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST, "Unsupported Method", message);
        return buildResponseEntityReturnsObject(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        var message = e.getConstraintViolations().stream().map(error -> error.getMessageTemplate() + " ").collect(Collectors.joining(" "));
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST, message, e.getLocalizedMessage());
        return buildResponseEntity(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED, "Unsupported Method", ex.getMessage());
        return buildResponseEntityReturnsObject(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST, "Request body is not receiving data", ex.getMessage());
        return buildResponseEntityReturnsObject(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        ErrorResponse errorResponse = new ErrorResponse(UNAUTHORIZED, "Invalid User or password", e.getMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        ErrorResponse errorResponse = new ErrorResponse(UNAUTHORIZED, "You don't have permission", e.getMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(UNAUTHORIZED, "Invalid", e.getMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(UNAUTHORIZED, "Invalid", e.getMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(CourseServiceNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleCourseServiceNotAvailableException(CourseServiceNotAvailableException e) {
        ErrorResponse errorResponse = new ErrorResponse(SERVICE_UNAVAILABLE, e.getMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(NoFallbackAvailableException.class)
    public ResponseEntity<ErrorResponse> handleNoFallbackAvailableException(NoFallbackAvailableException e) {
        ErrorResponse errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR, e.getMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<ErrorResponse> handleNCallNotPermittedException(CallNotPermittedException e) {
        ErrorResponse errorResponse = new ErrorResponse(SERVICE_UNAVAILABLE, e.getMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND, ex.getMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleTokenNotFoundException(TokenNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND, ex.getMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(PasswordsNotMatchException.class)
    protected ResponseEntity<ErrorResponse> handlePasswordsNotMatchException(PasswordsNotMatchException ex) {
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST, ex.getMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(PasswordException.class)
    protected ResponseEntity<ErrorResponse> handlePasswordException(PasswordException ex) {
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST, ex.getMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(UserException.class)
    protected ResponseEntity<ErrorResponse> handleUserException(UserException ex) {
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST, ex.getMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR, e.getMessage());
        return buildResponseEntity(errorResponse);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    private ResponseEntity<Object> buildResponseEntityReturnsObject(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
