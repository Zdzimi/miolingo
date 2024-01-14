package com.zdzimi.miolingo.service;

import com.zdzimi.miolingo.data.SigningUser;
import com.zdzimi.miolingo.data.model.UserEntity;
import com.zdzimi.miolingo.data.repository.UsersRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;

  public UserEntity signUp(SigningUser signingUser) {
    signingUser.setPassword(passwordEncoder.encode(signingUser.getPassword()));
    UserEntity userEntity = Mapper.map(signingUser);
    userEntity.setActivationCode(CodeUtils.getCode());
    return usersRepository.save(userEntity);
  }

  public void activateAccount(String code) {
    UserEntity userEntity = usersRepository.findByActivationCode(code)
        .orElseThrow(() -> new ActivationCodeNotFoundException(code));
    if (!halfHourHasPassed(code)) {
      userEntity.setActive(true);
      userEntity.setActivationCode(null);
      usersRepository.save(userEntity);
    } else {
      throw new ActivationCodeExpiredException(code);
    }
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
