package com.restorationservice.restorationv1.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.restorationservice.restorationv1.model.user.User;

public class SecurityUtils {

  public static String getAuthenticatedUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
      return ((UserDetails) authentication.getPrincipal()).getUsername();
    }
    return "UNKNOWN_USER";
  }

  public static String getAuthenticatedFullName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
      return ((User) authentication.getPrincipal()).getFirstname() + " "
          + ((User) authentication.getPrincipal()).getLastname();
    }
    return "UNKNOWN_USER";
  }
}
