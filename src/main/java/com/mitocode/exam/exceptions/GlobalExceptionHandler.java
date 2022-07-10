package com.mitocode.exam.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleExceptions(Exception ex, WebRequest request) {
        ExceptionResponseDTO dto = new ExceptionResponseDTO(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleModelNotFound(ModelNotFoundException ex, WebRequest request){
        ExceptionResponseDTO dto = new ExceptionResponseDTO(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream().map(error ->
                error.getField() + ": " + error.getDefaultMessage()
        ).collect(Collectors.joining(" "));

        ExceptionResponseDTO dto = new ExceptionResponseDTO(LocalDateTime.now(), message, request.getDescription(false));

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
