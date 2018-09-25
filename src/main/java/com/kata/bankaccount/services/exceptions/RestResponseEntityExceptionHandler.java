package com.kata.bankaccount.services.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = AccountNotFoundException.class)
    protected ResponseEntity<Object> accountNotFoundHandler(AccountNotFoundException ex, WebRequest request) {
        String bodyOfResponse = "Account Not Found !";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = ClientNotFoundException.class)
    protected ResponseEntity<Object> clientNotFoundHandler(ClientNotFoundException ex, WebRequest request) {
        String bodyOfResponse = "Client Not Found !";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value =  AccountAlreadyExistException.class)
    protected ResponseEntity<Object> accountAlreadyExistHandler(AccountAlreadyExistException ex, WebRequest request) {
        String bodyOfResponse = "Account Already Exists !";
        log.error(bodyOfResponse);
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.ALREADY_REPORTED, request);
    }

    @ExceptionHandler(value = AmountMinMaxValueException.class)
    protected ResponseEntity<Object> amountMinMaxValueException(AmountMinMaxValueException ex, WebRequest request) {
        String bodyOfResponse = "Bad Request ! amount's value is not valid ";
        log.error(bodyOfResponse);
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value =  AmountLowerThanBalanceException.class)
    protected ResponseEntity<Object> amountLowerThanBalanceException(AmountLowerThanBalanceException ex, WebRequest request) {
        String bodyOfResponse = "Bad Request ! amount's value is lower than balance";
        log.error(bodyOfResponse);
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}