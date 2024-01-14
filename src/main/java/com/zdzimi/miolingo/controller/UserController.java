package com.zdzimi.miolingo.controller;

import com.zdzimi.miolingo.data.SigningUser;
import com.zdzimi.miolingo.service.MailService;
import com.zdzimi.miolingo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/miolingo")
@RequiredArgsConstructor
public class UserController {

  private final UsersService usersService;
  private final MailService mailService;

  @PostMapping("/sign-up")
  public String signUp(@RequestBody SigningUser signingUser) {
    mailService.sendActivationCode(usersService.signUp(signingUser));
    return signingUser.getEmail();
  }

  @GetMapping("/activation/{code}")
  public void activateAccount(@PathVariable String code) {
    usersService.activateAccount(code);
  }

}
