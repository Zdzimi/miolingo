package com.zdzimi.miolingo.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.zdzimi.miolingo.controller.UserController;
import com.zdzimi.miolingo.data.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

  private final MailSender mailSender;

  public void sendActivationCode(UserEntity userEntity){
    SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject("Miolingo. Activation code.");
    message.setTo(userEntity.getEmail());
    message.setText(
        String.format(
            "Activate your account >>%s<<",
            linkTo(UserController.class)
                .slash("activation")
                .slash(userEntity.getActivationCode())
                .withRel("activation")
                .getHref()
        )
    );
    mailSender.send(message);
  }

}
