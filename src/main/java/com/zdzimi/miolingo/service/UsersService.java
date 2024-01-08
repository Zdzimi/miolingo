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

  public void signUp(SigningUser signingUser) {
    signingUser.setPassword(passwordEncoder.encode(signingUser.getPassword()));
    UserEntity userEntity = Mapper.map(signingUser);
    userEntity.setActivationCode(CodeUtils.getCode());
    usersRepository.save(userEntity);
  }

  private static class Mapper {

    public static UserEntity map(SigningUser signingUser) {
      UserEntity uE = new UserEntity();
      uE.setName(signingUser.getName());
      uE.setEmail(signingUser.getEmail());
      uE.setPassword(signingUser.getPassword());
      uE.setRole("ROLE_USER");
      uE.setActive(false);
      return uE;
    }

  }

  private static class CodeUtils {

    public static String getCode() {
      LocalDateTime now = LocalDateTime.now();
      StringBuilder sb = new StringBuilder();
      // (b10) 0 - 20000 -> (b16) 0 - 4e20
      sb.append(Integer.toHexString((int) (Math.random() * 20000 + 1)));
      sb.append("-");
      // (b10) 2024 - 2050 -> (b16) 7e8 - 802
      sb.append(Integer.toHexString(now.getYear()));
      sb.append("-");
      // (b10) 0 - 11 -> (b16) 0 - b
      sb.append(Integer.toHexString(now.getMonthValue()));
      sb.append("-");
      // (b10) 0 - 31 -> (b16) 0 - 1f
      sb.append(Integer.toHexString(now.getDayOfMonth()));
      sb.append("-");
      // (b10) 0 - 23 -> (b16) 0 - 17
      sb.append(Integer.toHexString(now.getHour()));
      sb.append("-");
      // (b10) 0 - 59 -> (b16) 0 - 3b
      sb.append(Integer.toHexString(now.getMinute()));
      sb.append("-");
      // (b10) 0 - 59 -> (b16) 0 - 3b
      sb.append(Integer.toHexString(now.getSecond()));
      return sb.toString();
    }

  }

}
