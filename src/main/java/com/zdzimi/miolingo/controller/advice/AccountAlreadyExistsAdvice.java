package com.zdzimi.miolingo.controller.advice;

import com.zdzimi.miolingo.service.AccountAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class AccountAlreadyExistsAdvice {

  @ExceptionHandler(AccountAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public String accountAlreadyExistsHandler(AccountAlreadyExistsException e) {
    return e.getMessage();
  }

}
