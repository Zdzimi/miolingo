package com.zdzimi.miolingo.controller.advice;

import com.zdzimi.miolingo.service.AccountAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class AccountAlreadyExistsAdvice {

  @ExceptionHandler(AccountAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String accountAlreadyExistsHandler(AccountAlreadyExistsException e) {
    return e.getMessage();
  }

}
