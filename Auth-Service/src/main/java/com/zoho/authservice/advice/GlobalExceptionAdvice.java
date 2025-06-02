package com.zoho.authservice.advice;

import com.zoho.authservice.dto.response.ErrorResponse;
import com.zoho.authservice.exception.EmptyValueException;
import com.zoho.authservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.zoho.authservice.constrain.GlobalConstrain.*;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(EmptyValueException.class)
    public ResponseEntity<?> handleEmptyValueException(EmptyValueException emptyValueException){
        return new ResponseEntity<>(VALUE_CANNOT_BE_NULL, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchMethodError.class)
    public ResponseEntity<?> handleNoSuchMethodError(NoSuchMethodError noSuchMethodError){
        return new ResponseEntity<>(NO_SUCH_METHOD_ERROR,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException errorResponse) {
        return new ResponseEntity<>(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception errorResponse) {
        return new ResponseEntity<>("ERROR", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .code("INTERNAL_ERROR")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
