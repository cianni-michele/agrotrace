package it.unicam.cs.agrotrace.shared.model.user;

import it.unicam.cs.agrotrace.shared.model.content.Content;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface User extends UserDetails {

    /**
     * Returns the identifier of the user.
     *
     * @return the identifier of the user
     */
    Long getId();

    /**
     * Returns the email of the user.
     *
     * @return the email of the user
     */
    default String getEmail(){
        return getUsername();
    }

    /**
     * Returns the name of the user.
     *
     * @return the name of the user
     */
    String getName();

    /**
     * Checks if the user has access to the given content.
     *
     * @param content the content to check access for
     * @return true if the user has access, false otherwise
     */
    boolean hasAccessTo(Content content);

    /**
     * Filters the given list of contents, returning only those that the user has access to.
     *
     * @param contents the list of contents to filter
     * @return a list of contents that the user has access to
     */
    default List<Content> filterAccessibleContents(List<Content> contents) {
        return contents.stream()
                .filter(this::hasAccessTo)
                .toList();
    }
}
