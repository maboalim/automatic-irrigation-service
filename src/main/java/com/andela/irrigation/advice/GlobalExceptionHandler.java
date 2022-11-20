package com.andela.irrigation.advice;

import com.andela.irrigation.enums.ErrorCode;
import com.andela.irrigation.exception.ResourceNotFoundException;
import com.andela.irrigation.payload.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@Log4j2
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper;


    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) throws Exception {
        return handleException(ex, request);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex, WebRequest webRequest, HttpServletRequest request) {
        log.error("Unexpected exception while processing request {} with exception {}", request.getRequestURL(), ex.getMessage());
        return handleNativeException(ex, webRequest, new Response(ErrorCode.UNEXPECTED_ERROR));
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex, WebRequest webRequest, HttpServletRequest request) {
        log.error("Unexpected exception while processing request {} with exception {}", request.getRequestURL(), ex.getMessage());
        return handleNativeException(ex, webRequest, new Response(ErrorCode.RESOURCE_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UnsupportedOperationException.class})
    protected ResponseEntity<?> handleUnsupportedOperation(UnsupportedOperationException ex, WebRequest webRequest, HttpServletRequest request) {
        log.error("Unexpected exception while processing request {} with exception {}", request.getRequestURL(), ex.getMessage());
        return handleNativeException(ex, webRequest, new Response(ErrorCode.UNEXPECTED_ERROR));
    }

//    @SneakyThrows
//    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
//    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//    protected ResponseEntity<?> handleGenericExceptions(HttpMediaTypeNotSupportedException ex, HttpServletRequest request, WebRequest webRequest) {
//        log.error("Unexpected error while processing request {} with exception {}", request.getRequestURL(), ex.getMessage());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        return handleExceptionInternal(ex, objectMapper.writeValueAsString(new Response(ErrorCode.UNSUPPORTED_MEDIA_TYPE)), httpHeaders, HttpStatus.UNSUPPORTED_MEDIA_TYPE, webRequest);
//    }

    @SneakyThrows
    private ResponseEntity<?> handleNativeException(Exception ex, WebRequest request, Response response) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, objectMapper.writeValueAsString(response), httpHeaders, HttpStatus.BAD_REQUEST, request);
    }

    @SneakyThrows
    private ResponseEntity<?> handleNativeException(Exception ex, WebRequest request, Response response, HttpStatus status) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, objectMapper.writeValueAsString(response), httpHeaders, status, request);
    }


}
