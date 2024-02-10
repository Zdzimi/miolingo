package com.zdzimi.miolingo.data.validation;

class SignUtils {

  static boolean isLowercaseLetter(char sign) {
    return sign >= 'a' && sign <= 'z';
  }

  static boolean isUppercaseLetter(char sign) {
    return sign >= 'A' && sign <= 'Z';
  }

  static boolean isDigit(char sign) {
    return sign >= '0' && sign <= '9';
  }

}
