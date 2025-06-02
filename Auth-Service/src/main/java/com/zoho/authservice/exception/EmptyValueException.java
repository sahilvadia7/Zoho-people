package com.zoho.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyValueException extends RuntimeException{
    public EmptyValueException(String message){
         super(message);
    }
}
