package com.intern.crm.audit;

import com.intern.crm.security.service.UserDetailsImpl;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    public static final String userAnymount = "11111111-1111-1111-1111-11111111111";

//    @Override
//    public Optional<String> getCurrentAuditor() {
//        return Optional.of("Administrator");
//    }
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.of("Administrator");
        }

        return ((UserDetailsImpl) authentication.getPrincipal()).getId().describeConstable();
    }
}
