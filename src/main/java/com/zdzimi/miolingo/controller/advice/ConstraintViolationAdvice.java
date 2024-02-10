package com.zdzimi.miolingo.controller.advice;

import com.zdzimi.miolingo.data.validation.Violation;
import com.zdzimi.miolingo.data.validation.ViolationsResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ConstraintViolationAdvice {

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ViolationsResponse onConstraintValidationException(ConstraintViolationException exception) {
    ViolationsResponse response = new ViolationsResponse();
    for (ConstraintViolation violation : exception.getConstraintViolations()) {
      response.getViolations().add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
    }
    return response;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ViolationsResponse onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    ViolationsResponse response = new ViolationsResponse();
    for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
      response.getViolations().add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
    }
    return response;
  }

}
