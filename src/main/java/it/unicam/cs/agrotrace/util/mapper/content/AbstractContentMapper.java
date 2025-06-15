package it.unicam.cs.agrotrace.util.mapper.content;

import it.unicam.cs.agrotrace.rest.view.ContentView;
import it.unicam.cs.agrotrace.shared.entity.content.ContentEntity;
import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.util.mapper.EntityMapper;
import it.unicam.cs.agrotrace.util.mapper.ViewMapper;
import it.unicam.cs.agrotrace.util.mapper.user.author.AuthorMapper;

abstract class AbstractContentMapper<V extends ContentView, M extends Content, E extends ContentEntity>
        implements ViewMapper<V, M>, EntityMapper<M, E> {

    protected final AuthorMapper authorMapper;

    protected AbstractContentMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }
}
