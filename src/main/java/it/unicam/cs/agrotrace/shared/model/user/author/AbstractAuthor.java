package it.unicam.cs.agrotrace.shared.model.user.author;

import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.user.AbstractUser;

public abstract class AbstractAuthor extends AbstractUser implements Author {

    protected AbstractAuthor(Long id, String name, String username, String password, Role role) {
        super(id, name, username, password, role);
    }

    @Override
    public String getEmail() {
        return this.getUsername();
    }

    @Override
    public boolean hasAccessTo(Content content) {
        return this.getId().equals(content.getAuthor().getId());
    }
}
