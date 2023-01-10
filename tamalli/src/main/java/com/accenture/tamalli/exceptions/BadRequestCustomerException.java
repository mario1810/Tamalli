package com.accenture.tamalli.exceptions;

public class BadRequestCustomerException extends CustomerException{
    public BadRequestCustomerException(String message) {
        super(message);
    }
}
