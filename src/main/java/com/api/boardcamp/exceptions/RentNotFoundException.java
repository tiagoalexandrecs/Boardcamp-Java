package com.api.boardcamp.exceptions;

public class RentNotFoundException extends RuntimeException{
    public RentNotFoundException(String message) {
        super(message);
    }
}
