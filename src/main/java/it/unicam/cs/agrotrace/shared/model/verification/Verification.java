package it.unicam.cs.agrotrace.shared.model.verification;

import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;
import it.unicam.cs.agrotrace.shared.model.user.admin.Curator;
import lombok.Builder;

@Builder
public record Verification(Long id, Curator curator, Content content, String comments) {

    /**
     * Returns the author's name of the content associated with this verification.
     *
     * @return the author's name of the content being verified
     */
    public String authorName() {
        return content.getAuthor().getName();
    }

    /**
     * Returns the author's email of the content associated with this verification.
     *
     * @return the author's email of the content being verified
     */
    public String authorEmail() {
        return content.getAuthor().getEmail();
    }

    /**
     * Returns the title of the content associated with this verification.
     *
     * @return the title of the content being verified
     */
    public String contentTitle() {
        return content.getTitle();
    }

    /**
     * Returns the status of the content associated with this verification.
     *
     * @return the validation status of the content
     */
    public ValidationStatus contentStatus() {
        return content.getValidationStatus();
    }
}
