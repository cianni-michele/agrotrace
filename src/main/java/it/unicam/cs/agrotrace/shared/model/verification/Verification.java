package it.unicam.cs.agrotrace.shared.model.verification;

import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.user.admin.Curator;
import lombok.Builder;

@Builder
public record Verification(Long id, Curator curator, Content content, String comments) {
}
