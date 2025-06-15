package it.unicam.cs.agrotrace.service;

import it.unicam.cs.agrotrace.shared.model.user.admin.Curator;
import it.unicam.cs.agrotrace.shared.model.user.author.Producer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    /* This is a simple example. In a real application, you would fetch user details from a database. */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("curator")) {
            return Curator.builder()
                    .id(0L)
                    .name("curator")
                    .username("curator")
                    .password("{noop}password") // {noop} indicates no password encoding
                    .build();
        }

        if (username.equals("producer")) {
            return Producer.builder()
                    .id(2L)
                    .name("producer")
                    .username("producer")
                    .password("{noop}password") // {noop} indicates no password encoding
                    .build();
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
