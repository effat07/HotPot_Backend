package com.hexaware.HotPot.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hexaware.HotPot.entity.User;

public class HotPotUserDetails implements UserDetails {

  private final String username;
  private final String password;
  private final boolean enabled;
  private final List<GrantedAuthority> authorities;

  public HotPotUserDetails(User u) {
    this.username = u.getEmail() != null ? u.getEmail() : u.getUserName();
    this.password = u.getPassword();
    this.enabled  = u.isActive();
    
    this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name()));
  }

  @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
  @Override public String getPassword() { return password; }
  @Override public String getUsername() { return username; }
  @Override public boolean isAccountNonExpired() { return true; }
  @Override public boolean isAccountNonLocked() { return true; }
  @Override public boolean isCredentialsNonExpired() { return true; }
  @Override public boolean isEnabled() { return enabled; }
}
