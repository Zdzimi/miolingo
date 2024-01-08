package com.zdzimi.miolingo.controller;

import com.zdzimi.miolingo.data.SigningUser;
import com.zdzimi.miolingo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/miolingo")
@RequiredArgsConstructor
public class UserController {

  private final UsersService usersService;

  @PostMapping("/sign-up")
  public String signUp(@RequestBody SigningUser signingUser) {
    usersService.signUp(signingUser);
    // todo send mail with activation code.
    return signingUser.getEmail();
  }

}
