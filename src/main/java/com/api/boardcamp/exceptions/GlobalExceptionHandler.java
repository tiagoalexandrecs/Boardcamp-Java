package com.api.boardcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    // User
    @ExceptionHandler({ CustomerNotFoundException.class })
    public ResponseEntity<Object> handlerCustomerNotFound(CustomerNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler({ GameNotFoundException.class })
    public ResponseEntity<Object> handlerGameNotFound(GameNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler({ RentNotFoundException.class })
    public ResponseEntity<Object> handlerGameNotFound(RentNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }


    @ExceptionHandler({ ConflictException.class })
    public ResponseEntity<Object> handlerConflict(ConflictException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({UnprocessableEntityException.class})
    public ResponseEntity<Object> handlerUnprocessableEntity(UnprocessableEntityException excpetion) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(excpetion.getMessage());
    }
}
