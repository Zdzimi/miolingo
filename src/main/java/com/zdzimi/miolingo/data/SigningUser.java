package com.zdzimi.miolingo.data;

import com.zdzimi.miolingo.data.validation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SigningUser {

  @Email(message = "Pass the e-mail address.")
  @NotEmpty(message = "E-mail is required.")
  private String email;
  @NotEmpty(message = "Name is required.")
  private String name;
  @Password
  private String password;

}
