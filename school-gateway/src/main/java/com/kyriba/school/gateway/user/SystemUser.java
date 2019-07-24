package com.kyriba.school.gateway.user;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @author M-ABO
 */
public class SystemUser {

  private SystemUser() {

  }

  public static boolean isAuthorized() {
    return getKeycloakPrincipal().isPresent();
  }

  public static Optional<AccessToken> getCurrentToken() {
    return getKeycloakPrincipal()
        .map(KeycloakPrincipal::getKeycloakSecurityContext)
        .map(KeycloakSecurityContext::getToken);
  }


  @SuppressWarnings("unchecked")
  private static Optional<KeycloakPrincipal<KeycloakSecurityContext>> getKeycloakPrincipal() {
    return Optional.ofNullable(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .filter(principal -> principal instanceof KeycloakPrincipal)
        .map(principal -> (KeycloakPrincipal<KeycloakSecurityContext>) principal);
  }
}
