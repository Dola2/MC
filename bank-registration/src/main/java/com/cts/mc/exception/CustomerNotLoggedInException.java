package com.cts.mc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class CustomerNotLoggedInException extends Exception{
	
	private static final long serialVersionUID = 1L;
	public CustomerNotLoggedInException() {
        super();
    }
   

}
