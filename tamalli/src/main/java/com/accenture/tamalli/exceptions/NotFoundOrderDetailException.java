package com.accenture.tamalli.exceptions;

public class NotFoundOrderDetailException extends OrderDetailException{
    public NotFoundOrderDetailException(String message) {
        super(message);
    }
}
