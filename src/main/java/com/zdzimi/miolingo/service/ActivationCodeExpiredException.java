package com.zdzimi.miolingo.service;

public class ActivationCodeExpiredException extends RuntimeException {

  public ActivationCodeExpiredException(String code) {
    super(String.format("Activation code: %s has expired."));
  }

  // todo create controller advice

}
