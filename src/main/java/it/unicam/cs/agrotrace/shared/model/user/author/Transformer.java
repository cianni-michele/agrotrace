package it.unicam.cs.agrotrace.shared.model.user.author;

import lombok.Builder;

public class Transformer extends AbstractAuthor {
    @Builder
    public Transformer(Long id, String name, String username, String password) {
        super(id, name, username, password, Role.TRANSFORMER);
    }
}
