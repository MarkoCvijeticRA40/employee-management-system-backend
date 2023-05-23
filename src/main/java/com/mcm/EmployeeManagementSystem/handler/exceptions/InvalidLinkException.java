package com.mcm.EmployeeManagementSystem.handler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidLinkException extends RuntimeException {
    public InvalidLinkException() {
        super();
    }
}
