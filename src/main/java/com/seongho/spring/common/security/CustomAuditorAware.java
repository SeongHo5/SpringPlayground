package com.seongho.spring.common.security;

import com.seongho.spring.common.exception.AuthException;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.seongho.spring.common.exception.enums.ExceptionStatus.NO_AUTHORIZATION;

@Component
public class CustomAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthException(NO_AUTHORIZATION);
        }
        return Optional.ofNullable(authentication.getName());
    }
}

