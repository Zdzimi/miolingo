package com.zdzimi.miolingo.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class MailExceptionHandler {

  @ExceptionHandler(MailException.class)
  @ResponseStatus(HttpStatus.BAD_GATEWAY)
  public String mailExceptionHandler(MailException e) {
    return e.getMessage();
  }

}
