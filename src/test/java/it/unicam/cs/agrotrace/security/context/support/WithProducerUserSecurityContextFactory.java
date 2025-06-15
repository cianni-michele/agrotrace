package it.unicam.cs.agrotrace.security.context.support;

import it.unicam.cs.agrotrace.shared.model.user.author.Producer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

class WithProducerUserSecurityContextFactory implements WithSecurityContextFactory<WithProducerUser> {

    @Override
    public SecurityContext createSecurityContext(WithProducerUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Producer producer = new Producer(1L, "Producer", "producer", "{noop}password");
        Authentication auth = new UsernamePasswordAuthenticationToken(
                producer, producer.getPassword(), producer.getAuthorities()
        );
        context.setAuthentication(auth);
        return context;
    }
}
