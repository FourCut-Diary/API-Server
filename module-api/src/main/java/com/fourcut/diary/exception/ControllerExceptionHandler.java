package com.fourcut.diary.exception;

import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    /*
    400 BAD REQUEST
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            builder.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append("\n");
        }

        log.error(builder.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(ErrorMessage.INVALID_METHOD_ARGUMENT));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {

        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(ErrorMessage.HTTP_MESSAGE_NOT_READABLE));
    }

    /*
    405 METHOD NOT ALLOWED
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {

        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ErrorResponse.of(ErrorMessage.METHOD_NOT_ALLOWED));
    }

    /*
    500 INTERNAL SERVER ERROR
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception error) {

        log.error("\uD83D\uDEA8INTERN_SERVER_ERROR\uD83D\uDEA8: {}", error.getMessage(), error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.of(ErrorMessage.INTERNAL_SERVER_ERROR));
    }
}
