package com.hexaware.HotPot.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.hexaware.HotPot.entity.User;
import com.hexaware.HotPot.repository.UserRepository;

@Service
public class HotPotUserDetailsService implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    Optional<User> byEmail = usernameOrEmail.contains("@")
        ? userRepository.findByEmail(usernameOrEmail)
        : Optional.empty();
    User user = byEmail.or(() -> userRepository.findByUserName(usernameOrEmail))
                       .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail));
    return new HotPotUserDetails(user);
  }
}
