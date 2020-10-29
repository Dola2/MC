package com.cts.mc.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cts.mc.exception.LoanNotFoundException;
import com.cts.mc.model.ApiErrorResponse;


@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({LoanNotFoundException.class})
    public ResponseEntity <ApiErrorResponse> customerNotFound(LoanNotFoundException ex) {
        ApiErrorResponse apiResponse = new ApiErrorResponse
            .ApiErrorResponseBuilder()
            .withDetail("Not able to find Loan record")
            .withMessage("Not a valid Loan id.")
            .withError_code("404")
            .withStatus(HttpStatus.NOT_FOUND)
            .atTime(LocalDateTime.now(ZoneOffset.UTC))
            .build();
        return new ResponseEntity <ApiErrorResponse> (apiResponse, HttpStatus.NOT_FOUND);        
    }
}
