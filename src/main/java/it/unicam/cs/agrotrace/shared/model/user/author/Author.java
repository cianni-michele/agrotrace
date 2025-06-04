package it.unicam.cs.agrotrace.shared.model.user.author;

public interface Author {

    /**
     * Returns the identifier of the author.
     *
     * @return the identifier of the author.
     */
    Long getId();

    /**
     * Returns the name of the author.
     *
     * @return the name of the author.
     */
    String getName();

    /**
     * Returns the email of the author.
     *
     * @return the email of the author.
     */
    String getEmail();

}
