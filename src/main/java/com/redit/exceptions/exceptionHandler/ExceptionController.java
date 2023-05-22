package com.redit.exceptions.exceptionHandler;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ExceptionTemplate> allExceptions(Exception exp){
        ExceptionTemplate exceptionTemplate=new ExceptionTemplate();
        exceptionTemplate.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionTemplate.setMessage(exp.getMessage());
        exceptionTemplate.setTimstamp(System.currentTimeMillis());
        return new ResponseEntity<ExceptionTemplate>(exceptionTemplate,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ExceptionTemplate> jwtExceptions(JwtException exp){
        ExceptionTemplate exceptionTemplate=new ExceptionTemplate();
        exceptionTemplate.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionTemplate.setMessage(exp.getMessage());
        exceptionTemplate.setTimstamp(System.currentTimeMillis());
        return new ResponseEntity<ExceptionTemplate>(exceptionTemplate,HttpStatus.BAD_REQUEST);
    }
}
