package com.zdzimi.miolingo.service;

import com.zdzimi.miolingo.data.SigningUser;
import com.zdzimi.miolingo.data.SubmittedUser;
import com.zdzimi.miolingo.data.User;
import com.zdzimi.miolingo.data.model.UserEntity;
import com.zdzimi.miolingo.data.repository.UsersRepository;
import com.zdzimi.miolingo.security.PrincipalUser;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@RequiredArgsConstructor
public class UsersService {

  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;
  private final MailService mailService;

  public SubmittedUser signUp(SigningUser signingUser) {
    if (hasActiveAccount(signingUser)) {
      throw new AccountAlreadyExistsException(signingUser.getEmail());
    }
    UserEntity savedUser = saveUser(signingUser);
    mailService.sendActivationCode(savedUser);
    return Mapper.map(savedUser);
  }

  public SubmittedUser activateAccount(String code) {
    if (halfHourHasPassed(code)) {
      throw new ActivationCodeExpiredException(code);
    }
    UserEntity userEntity = usersRepository.findByActivationCode(code)
        .orElseThrow(() -> new ActivationCodeNotFoundException(code));
    userEntity.setActive(true);
    userEntity.setActivationCode(null);
    return Mapper.map(usersRepository.save(userEntity));
  }

  public User getUser(PrincipalUser principalUser) {
    return Mapper.map(principalUser);
  }

  private boolean hasActiveAccount(SigningUser signingUser) {
    Optional<UserEntity> entityOptional = usersRepository.findById(signingUser.getEmail());
    return entityOptional.isPresent() && entityOptional.get().isActive();
  }

  private UserEntity saveUser(SigningUser signingUser) {
    signingUser.setPassword(passwordEncoder.encode(signingUser.getPassword()));
    UserEntity userEntity = Mapper.map(signingUser);
    userEntity.setActivationCode(CodeUtils.getCode());
    return usersRepository.save(userEntity);
  }

  private boolean halfHourHasPassed(String code) {
    return LocalDateTime.now().isAfter(CodeUtils.getDateTime(code).plusMinutes(30));
  }

  private static class Mapper {

    public static UserEntity map(SigningUser signingUser) {
      UserEntity uE = new UserEntity();
      uE.setName(signingUser.getName());
      uE.setEmail(signingUser.getEmail());
      uE.setPassword(signingUser.getPassword());
      uE.setRole("ROLE_USER");
      return uE;
    }

    public static SubmittedUser map(UserEntity userEntity) {
      SubmittedUser submittedUser = new SubmittedUser();
      submittedUser.setEmail(HtmlUtils.htmlEscape(userEntity.getEmail()));
      submittedUser.setName(HtmlUtils.htmlEscape(userEntity.getName()));
      return submittedUser;
    }

    public static User map(PrincipalUser principalUser) {
      User user = new User();
      user.setEmail(principalUser.getUsername());
      user.setName(principalUser.getUserEntity().getName());
      return user;
    }
  }

  private static class CodeUtils {

    public static String getCode() {
      LocalDateTime now = LocalDateTime.now();
      StringBuilder sb = new StringBuilder();
      sb.append(Integer.toHexString((int) (Math.random() * 20000 + 1)));
      sb.append("-");
      sb.append(Integer.toHexString(now.getYear()));
      sb.append("-");
      sb.append(Integer.toHexString(now.getMonthValue()));
      sb.append("-");
      sb.append(Integer.toHexString(now.getDayOfMonth()));
      sb.append("-");
      sb.append(Integer.toHexString(now.getHour()));
      sb.append("-");
      sb.append(Integer.toHexString(now.getMinute()));
      sb.append("-");
      sb.append(Integer.toHexString(now.getSecond()));
      return sb.toString();
    }

    public static LocalDateTime getDateTime(String code) {
      String[] split = code.split("-");
      return LocalDateTime.of(
          Integer.parseInt(split[1], 16),
          Integer.parseInt(split[2], 16),
          Integer.parseInt(split[3], 16),
          Integer.parseInt(split[4], 16),
          Integer.parseInt(split[5], 16),
          Integer.parseInt(split[6], 16)
      );
    }

  }

}
