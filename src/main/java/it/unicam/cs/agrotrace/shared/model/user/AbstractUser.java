package it.unicam.cs.agrotrace.shared.model.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public abstract class AbstractUser implements User {
    protected final Long id;
    protected final String name;
    @ToString.Exclude
    protected final String username;
    @ToString.Exclude
    protected final String password;
    @ToString.Exclude
    protected final Role role;

    protected AbstractUser(Long id, String name, String username, String password, Role role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + role.name())
        );
    }

    protected enum Role {
        CURATOR,
        DISTRIBUTOR,
        PRODUCER,
        TRANSFORMER
    }
}
