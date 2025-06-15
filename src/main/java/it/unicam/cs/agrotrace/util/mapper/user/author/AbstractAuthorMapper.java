package it.unicam.cs.agrotrace.util.mapper.user.author;

import it.unicam.cs.agrotrace.shared.entity.user.author.AuthorEntity;
import it.unicam.cs.agrotrace.shared.model.user.author.Author;
import it.unicam.cs.agrotrace.util.mapper.EntityMapper;

abstract class AbstractAuthorMapper<M extends Author, E extends AuthorEntity> implements EntityMapper<M, E> {
}
