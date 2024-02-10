package com.zdzimi.miolingo.controller.advice;

import com.zdzimi.miolingo.service.ActivationCodeExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ActivationCodeExpiredAdvice {

  @ExceptionHandler(ActivationCodeExpiredException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String activationCodeExpiredHandler(ActivationCodeExpiredException e, Model model) {
    model.addAttribute("exception", e.getMessage());
    model.addAttribute("title", "Activation code expired.");
    return "exception-page";
  }

}
