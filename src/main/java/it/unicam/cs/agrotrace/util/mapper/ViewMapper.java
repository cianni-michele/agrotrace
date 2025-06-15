package it.unicam.cs.agrotrace.util.mapper;

/**
 * A generic interface for mapping between model objects and view objects in a RESTful context.
 * This interface defines methods to convert a view object to a model object and vice versa.
 *
 * @param <V> the type of the view object
 * @param <M> the type of the model object
 */
public interface ViewMapper<V, M> {

    /**
     * Maps a model object to a view object.
     *
     * @param model the model object to be mapped
     * @return the mapped view object
     */
    V viewFromModel(M model);
}
