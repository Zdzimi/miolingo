package com.zdzimi.miolingo.controller;

import com.zdzimi.miolingo.data.SigningUser;
import com.zdzimi.miolingo.data.SubmittedUser;
import com.zdzimi.miolingo.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin
@Validated
@RestController
@RequestMapping("/miolingo")
@RequiredArgsConstructor
public class UserController {

  private final UsersService usersService;

  @PostMapping("/sign-up")
  public SubmittedUser signUp(@Valid @RequestBody SigningUser signingUser) {
    return usersService.signUp(signingUser);
  }

  @GetMapping("/activation/{code}")
  public ModelAndView activateAccount(@PathVariable String code) {
    SubmittedUser submittedUser = usersService.activateAccount(code);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("activation-completed");
    modelAndView.addObject("email", submittedUser.getEmail());
    modelAndView.addObject("name", submittedUser.getName());
    return modelAndView;
  }

}
