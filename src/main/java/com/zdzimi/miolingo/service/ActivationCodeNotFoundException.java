package com.zdzimi.miolingo.service;

public class ActivationCodeNotFoundException extends RuntimeException {

  public ActivationCodeNotFoundException(String code) {
    super(String.format("Activation code: %s not found", code));
  }

  // todo Create controller advice

}
