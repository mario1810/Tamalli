package com.accenture.tamalli.customExceptions;

public class CustomerException extends RuntimeException{

    public CustomerException(String message){
        super(message);
    }
}
