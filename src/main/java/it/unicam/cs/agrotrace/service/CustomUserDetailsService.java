package it.unicam.cs.agrotrace.service;

import it.unicam.cs.agrotrace.shared.model.user.admin.Curator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // This is a simple example. In a real application, you would fetch user details from a database.
        if (username.equals("curator")) {
            return Curator.builder()
                    .id(0L)
                    .name("curator")
                    .username("curator")
                    .password("{noop}password") // {noop} indicates no password encoding
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
