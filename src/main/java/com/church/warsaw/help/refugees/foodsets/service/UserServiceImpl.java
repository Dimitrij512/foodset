package com.church.warsaw.help.refugees.foodsets.service;

import com.church.warsaw.help.refugees.foodsets.config.UserConfiguration;
import com.church.warsaw.help.refugees.foodsets.dto.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService {

  private final UserConfiguration userConfiguration;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    if (!userConfiguration.getUsername().equals(username)) {
      throw new UsernameNotFoundException(username);
    }

    return User.builder()
        .username(userConfiguration.getUsername())
        .password(bCryptPasswordEncoder.encode(userConfiguration.getPassword()))
        .role(userConfiguration.getRole())
        .build();
  }
}
