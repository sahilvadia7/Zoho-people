package com.zoho.organizationservice.advice;


import com.zoho.organizationservice.exception.EmptyValueException;
import com.zoho.organizationservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.zoho.organizationservice.constrain.GlobalConstrain.USER_NOT_FOUND;
import static com.zoho.organizationservice.constrain.GlobalConstrain.VALUE_CANNOT_BE_NULL;
import static org.aspectj.apache.bcel.ExceptionConstants.NO_SUCH_METHOD_ERROR;


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

}
