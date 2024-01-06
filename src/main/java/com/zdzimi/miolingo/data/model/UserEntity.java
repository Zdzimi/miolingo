package com.zdzimi.miolingo.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

  @Id
  private String email;
  private String name;
  private String password;
  private String role;
  private boolean isActive;
  private String activationCode;

}
