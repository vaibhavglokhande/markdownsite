package com.markdownsite.web.controller;

import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.interfaces.ErrorCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Controller
public class ExceptionController implements ErrorController {

    public static final String DEFAULT_EXCEPTION_VIEW = "exception";

    @ExceptionHandler(value = AbstractException.class)
    public ModelAndView handleException(AbstractException exception) {
        ModelAndView modelAndView = new ModelAndView(DEFAULT_EXCEPTION_VIEW);
        ErrorCode errorCode = exception.getErrorCode();
        modelAndView.addObject("errorCode", errorCode.getExceptionId() + ":" + errorCode.getErrorCode());
        modelAndView.addObject("errorMessage", errorCode.getErrorMessage());
        if (!errorCode.getErrorMessage().equalsIgnoreCase(exception.getMessage()))
            modelAndView.addObject("exceptionMessage", exception.getMessage());
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return modelAndView;
    }

    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
