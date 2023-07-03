package com.mcm.EmployeeManagementSystem.handler;

import com.mcm.EmployeeManagementSystem.handler.exceptions.InvalidLinkException;
import com.mcm.EmployeeManagementSystem.handler.exceptions.InvalidTokenException;
import com.mcm.EmployeeManagementSystem.handler.exceptions.TokenLinkIsAlreadyUsedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TokenLinkIsAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorResponse handleTokenLinkIsAlreadyUsedException(TokenLinkIsAlreadyUsedException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("This link is already used!");
        return errorResponse;
    }

    @ExceptionHandler(InvalidLinkException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorResponse handleInvalidLinkException(InvalidLinkException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Invalid link!");
        return errorResponse;
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorResponse handleInvalidTokenException(InvalidTokenException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Invalid token!");
        return errorResponse;
    }
}
