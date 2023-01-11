package com.accenture.tamalli.handler;

import com.accenture.tamalli.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value=CustomerException.class)
    public ResponseEntity<Object> handleCustomerException(CustomerException e, WebRequest request) {

        HttpStatus status=null;
        if(e instanceof BadRequestCustomerException)
            status=HttpStatus.BAD_REQUEST;
        else if (e instanceof NotFoundCustomerException)
            status=HttpStatus.NOT_FOUND;
        else
            status=HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException=new ApiException(e.getMessage(),e.getClass().toString(),status,ZonedDateTime.now().now());
        //return response entity
        return new ResponseEntity<>(apiException, apiException.getHttpStatus());
    }

    @ExceptionHandler(value= OrderDetailException.class)
    public ResponseEntity<Object> handleOrderDetailException(OrderDetailException e, WebRequest request) {


        //create payload containing exception details
        HttpStatus status=null;
        if (e instanceof NotFoundOrderDetailException)
            status=HttpStatus.NOT_FOUND;
        else
            status=HttpStatus.BAD_REQUEST;
        ApiException apiException=new ApiException(e.getMessage(),e.getClass().toString(),status,ZonedDateTime.now().now());
        //return response entity

        return new ResponseEntity<>(apiException, apiException.getHttpStatus());
    }

    @ExceptionHandler(value= OrderException.class)
    public ResponseEntity<Object> handleOrderException(OrderException e, WebRequest request) {

        //create payload containing exception details
        HttpStatus status=null;
        if (e instanceof BadRequestOrderException)
            status=HttpStatus.BAD_REQUEST;
        else
            status=HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException=new ApiException(e.getMessage(),e.getClass().toString(),status,ZonedDateTime.now().now());
        //return response entity
        return new ResponseEntity<>(apiException, apiException.getHttpStatus());
    }

    @ExceptionHandler(value= ProductDescriptionException.class)
    public ResponseEntity<Object> handleProductDescriptionException(ProductDescriptionException e, WebRequest request) {

        //create payload containing exception details
        HttpStatus status=null;
        if (e instanceof NotFoundProductDescriptionException)
            status=HttpStatus.NOT_FOUND;
        else
            status=HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException=new ApiException(e.getMessage(),e.getClass().toString(),status,ZonedDateTime.now().now());
        //return response entity
        return new ResponseEntity<>(apiException, apiException.getHttpStatus());
    }

    @ExceptionHandler(value= ProductException.class)
    public ResponseEntity<Object> handleProductException(ProductException e, WebRequest request) {

        //create payload containing exception details
        HttpStatus status=null;
        if(e instanceof BadRequestProductException)
            status=HttpStatus.BAD_REQUEST;
        else if (e instanceof NotFoundProductException)
            status=HttpStatus.NOT_FOUND;
        else
            status=HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException=new ApiException(e.getMessage(),e.getClass().toString(),status,ZonedDateTime.now().now());
        //return response entity
        return new ResponseEntity<>(apiException, apiException.getHttpStatus());
    }

    @ExceptionHandler(value= DateTimeException.class)
    public ResponseEntity<Object> handleDateParseException(DateTimeException e, WebRequest request) {

        HttpStatus status=null;
        if(e instanceof DateTimeParseException)
            status=HttpStatus.BAD_REQUEST;
        else
            status=HttpStatus.INTERNAL_SERVER_ERROR; //Exception used to indicate a problem while calculating a date-time.
        ApiException apiException=new ApiException(e.getMessage(),e.getClass().toString(),status,ZonedDateTime.now().now());
        //return response entity
        return new ResponseEntity<>(apiException, apiException.getHttpStatus());
    }

    private class ApiException{
        private final String message;
        private final String className;
        private final HttpStatus httpStatus;
        private final ZonedDateTime timestamp;

        public ApiException(String message, String className, HttpStatus httpStatus, ZonedDateTime timestamp) {
            this.message = message;
            this.className = className;
            this.httpStatus = httpStatus;
            this.timestamp = timestamp;
        }

        public String getMessage() {
            return message;
        }

        public String getClassName() {
            return className;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public ZonedDateTime getTimestamp() {
            return timestamp;
        }


    }
}
