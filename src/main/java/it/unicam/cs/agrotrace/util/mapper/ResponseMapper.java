package it.unicam.cs.agrotrace.util.mapper;

/**
 * A generic interface for mapping between model objects and response objects in a RESTful context.
 *
 * @param <M> the type of the model object
 * @param <R> the type of the response object
 */
public interface ResponseMapper<M, R>{

    /**
     * Maps a model object to a response object.
     *
     * @param model the model object to be mapped
     * @return the mapped response object
     */
    R responseFromModel(M model);
}
