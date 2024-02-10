package com.zdzimi.miolingo.controller.advice;

import com.zdzimi.miolingo.service.ActivationCodeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ActivationCodeNotFoundAdvice {

  @ExceptionHandler(ActivationCodeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String activationCodeNotFoundHandler(ActivationCodeNotFoundException e, Model model) {
    model.addAttribute("exception", e.getMessage());
    model.addAttribute("title", "Could not find code.");
    return "exception-page";
  }

}
