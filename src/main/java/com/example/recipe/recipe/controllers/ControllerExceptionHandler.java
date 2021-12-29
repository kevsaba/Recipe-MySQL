package com.example.recipe.recipe.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleBadRequest(Exception ex) {
        log.error("Handling bad request exception");
        log.error(ex.getMessage());
        ModelAndView view = new ModelAndView("400error");
        view.addObject("exception",ex);
        return view;
    }
}
