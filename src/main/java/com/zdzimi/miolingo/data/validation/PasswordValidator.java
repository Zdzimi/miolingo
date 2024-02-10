package com.zdzimi.miolingo.data.validation;

import static com.zdzimi.miolingo.data.validation.SignUtils.*;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    boolean containsLowercaseLetter = false;
    boolean containsUppercaseLetter = false;
    boolean containsDigit = false;
    if (value == null) {
      return false;
    }
    for (int i = 0; i < value.length(); i++) {
      if (isLowercaseLetter(value.charAt(i))) {
        containsLowercaseLetter = true;
      } else if (isUppercaseLetter(value.charAt(i))) {
        containsUppercaseLetter = true;
      } else if (isDigit(value.charAt(i))) {
        containsDigit = true;
      }
    }
    return containsLowercaseLetter && containsUppercaseLetter && containsDigit;
  }

}
