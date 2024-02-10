package com.zdzimi.miolingo.security;

import com.zdzimi.miolingo.data.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MiolingoDetailsService implements UserDetailsService {

  private final UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return new PrincipalUser(usersRepository.findById(email)
        .orElseThrow(() -> new UsernameNotFoundException(String.format("404 User %s not found.", email))));
  }

}
