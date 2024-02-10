package com.zdzimi.miolingo.service;

public class AccountAlreadyExistsException extends RuntimeException {

  public AccountAlreadyExistsException(String email) {
    super(String.format("Account %s already exists.", email));
  }

}
