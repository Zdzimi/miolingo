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

  }

}
