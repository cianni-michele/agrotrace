package it.unicam.cs.agrotrace.shared.model.user.author;

import lombok.Builder;

public class Producer extends AbstractAuthor {

    @Builder
    public Producer(Long id, String name, String username, String password) {
        super(id, name, username, password, Role.PRODUCER);
    }
}
