package it.unicam.cs.agrotrace.util.mapper;

/**
 * A generic interface for mapping between model objects and entity objects.
 * This interface defines methods to convert a model object to an entity object and vice versa.
 *
 * @param <M> the type of the model object
 * @param <E> the type of the entity object
 */
public interface RepositoryMapper<M, E> {

    /**
     * Maps a model object to an entity object.
     *
     * @param model the model object to be mapped
     * @return the mapped entity object
     */
    E entityFromModel(M model);

    /**
     * Maps an entity object to a model object.
     *
     * @param entity the entity object to be mapped
     * @return the mapped model object
     */
    M modelFromEntity(E entity);
}
