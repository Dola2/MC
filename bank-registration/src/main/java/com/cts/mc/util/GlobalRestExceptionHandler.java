package com.cts.mc.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cts.mc.exception.CustomerNotFoundException;
import com.cts.mc.exception.CustomerNotLoggedInException;
import com.cts.mc.model.ApiErrorResponse;


@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
	private static final  String  CUST_NOT_LOGIN = "Customer not Logged in";

    @ExceptionHandler({CustomerNotFoundException.class})
    public ResponseEntity <ApiErrorResponse> customerNotFound(CustomerNotFoundException ex) {
        ApiErrorResponse apiResponse = new ApiErrorResponse
            .ApiErrorResponseBuilder()
            .withDetail("Not able to find customer record")
            .withMessage("Not a valid user id.Please provide a valid user id or contact system admin.")
            .withError_code("404")
            .withStatus(HttpStatus.NOT_FOUND)
            .atTime(LocalDateTime.now(ZoneOffset.UTC))
            .build();
        return new ResponseEntity <ApiErrorResponse> (apiResponse, HttpStatus.NOT_FOUND);        
    }
    @ExceptionHandler({CustomerNotLoggedInException.class})
    public ResponseEntity <ApiErrorResponse> customerNotLoggedIn(CustomerNotLoggedInException ex) {
        ApiErrorResponse apiResponse = new ApiErrorResponse
            .ApiErrorResponseBuilder()
            .withDetail(CUST_NOT_LOGIN)
            .withMessage(CUST_NOT_LOGIN)
            .withError_code("412")
            .withStatus(HttpStatus.PRECONDITION_FAILED)
            .atTime(LocalDateTime.now(ZoneOffset.UTC))
            .build();
        return new ResponseEntity <ApiErrorResponse> (apiResponse, HttpStatus.PRECONDITION_FAILED);        
    }
}
