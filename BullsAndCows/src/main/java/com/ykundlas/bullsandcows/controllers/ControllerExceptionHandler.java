/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ykundlas.bullsandcows.controllers;

import com.ykundlas.bullsandcows.service.CorrectNumberOfDigitsException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.ykundlas.bullsandcows.service.GameCompletionException;

/**
 *
 * @author Yash
 */
@ControllerAdvice
@RestController
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String CONSTRAINT_MESSAGE = "Could not save your item. "
            + "Please ensure it is valid and try again.";

    private static final String GAME_COMPLETED = "Game is completed";
    
    private static final String DIGITS_MESSAGE = "Number of digits in guess should be 4";

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<Error> handleSqlException(
            SQLIntegrityConstraintViolationException ex,
            WebRequest request) {

        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE);
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);

    }


    @ExceptionHandler(GameCompletionException.class)
    public final ResponseEntity<Error> handlesqlException(GameCompletionException ex, WebRequest request){
        Error err = new Error();
        err.setMessage(GAME_COMPLETED);
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(CorrectNumberOfDigitsException.class)
    public final ResponseEntity<Error> handlesqlException(CorrectNumberOfDigitsException ex, WebRequest request){
        Error err = new Error();
        err.setMessage(DIGITS_MESSAGE);
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
    
     

}
