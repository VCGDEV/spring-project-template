package com.vcgdev.demo.config.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {
    
    public String getAuthenticatedUser() {
        Authentication authentication = getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }
    
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public boolean isUserInRole(String role) {
        try {
            return getAuthentication().getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
        } catch (Exception e) {
                return false;
        }
    }
    
}
