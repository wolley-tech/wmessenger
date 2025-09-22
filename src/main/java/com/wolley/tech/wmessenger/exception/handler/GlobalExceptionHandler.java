package com.wolley.tech.wmessenger.exception.handler;


import com.wolley.tech.wmessenger.exception.ContactAgentNotFoundException;
import com.wolley.tech.wmessenger.exception.ContactNotFoundException;
import com.wolley.tech.wmessenger.exception.InvalidParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String REQUEST_HEADER_PARAM = "O parâmetro %s é obrigatório no header";

    @ExceptionHandler(value = {
            ContactAgentNotFoundException.class,
            ContactNotFoundException.class})
    public ProblemDetail notFoundExceptionHandler(Exception ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }


    @ExceptionHandler(value = {
            InvalidParameterException.class})
    public ProblemDetail invalidParameterExceptionHandler(InvalidParameterException ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }

    @ExceptionHandler(value = {
            MissingRequestHeaderException.class})
    public ProblemDetail headerParameterExceptionHandler(MissingRequestHeaderException ex) {
        return ProblemDetail.forStatusAndDetail(
                ex.getStatusCode(),
                String.format(REQUEST_HEADER_PARAM, ex.getHeaderName())
        );
    }


}
