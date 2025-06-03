package it.unicam.cs.agrotrace.security.context.support;

import it.unicam.cs.agrotrace.shared.model.user.admin.Curator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

class WithCuratorUserSecurityContextFactory implements WithSecurityContextFactory<WithCuratorUser> {

    @Override
    public SecurityContext createSecurityContext(WithCuratorUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Curator curator = new Curator(1L, "Curator", "curator", "{noop}password");
        Authentication auth = new UsernamePasswordAuthenticationToken(
                curator, curator.getPassword(), curator.getAuthorities()
        );
        context.setAuthentication(auth);
        return context;
    }
}
