package com.notesapp.notesapp.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception exception) {
        ModelAndView page = new ModelAndView();
        page.setViewName("login.html");
        page.addObject("errors", exception.getMessage());
        return page;
    }

}
