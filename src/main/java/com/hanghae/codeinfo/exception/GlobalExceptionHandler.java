package com.hanghae.codeinfo.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


//@ControllerAdvice
//
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(Exception.class)
//    public String except(Exception ex, Model model) {
//        String exception = ex.getMessage();
//        System.out.println(exception);
//        model.addAttribute("exception", exception);
//
//        return "error_page";
//    }
//}