package it.unicam.cs.agrotrace.util.mapper;

/**
 * A generic interface for mapping between request objects and model objects in a RESTful context.
 * @param <R> the type of the request object
 * @param <M> the type of the model object
 */
public interface RequestMapper<R, M > {

    /**
     * Maps a request object to a model object.
     *
     * @param request the request object to be mapped
     * @return the mapped model object
     */
    M modelFromRequest(R request);

}
