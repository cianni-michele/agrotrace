package it.unicam.cs.agrotrace.shared.model.user.author;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Distributor extends AbstractAuthor {

    @Builder
    public Distributor(Long id, String name, String username, String password) {
        super(id, name, username, password, Role.DISTRIBUTOR);
    }
}
