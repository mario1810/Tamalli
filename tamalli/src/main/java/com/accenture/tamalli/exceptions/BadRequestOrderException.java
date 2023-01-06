package com.accenture.tamalli.exceptions;

public class BadRequestOrderException extends OrderException{
    public BadRequestOrderException(String message) {
        super(message);
    }
}
