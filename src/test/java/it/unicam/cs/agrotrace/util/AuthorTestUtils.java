package it.unicam.cs.agrotrace.util;

import it.unicam.cs.agrotrace.shared.entity.user.author.AuthorEntity;
import it.unicam.cs.agrotrace.shared.entity.user.author.DistributorEntity;
import it.unicam.cs.agrotrace.shared.entity.user.author.ProducerEntity;
import it.unicam.cs.agrotrace.shared.entity.user.author.TransformerEntity;
import it.unicam.cs.agrotrace.shared.model.user.author.Author;
import it.unicam.cs.agrotrace.shared.model.user.author.Distributor;
import it.unicam.cs.agrotrace.shared.model.user.author.Producer;
import it.unicam.cs.agrotrace.shared.model.user.author.Transformer;

public final class AuthorTestUtils {

    /** This is the ID of the author who created the bundle **/
    public static final long TEST_DISTRIBUTOR_ID = 1L;

    /** This is the ID of the author who created the process **/
    public static final long TEST_TRANSFORMER_ID = 2L;

    /** This is the ID of the author who created the product **/
    public static final long TEST_PRODUCER_ID = 3L;

    public static Author buildTestDistributorAuthor(Long authorId) {
        return Distributor.builder()
                .id(authorId)
                .name("Test Distributor")
                .username("testdistributor")
                .password("{noop}password")
                .build();
    }

    public static Author buildTestTransformerAuthor(Long authorId) {
        return Transformer.builder()
                .id(authorId)
                .name("Test Transformer")
                .username("testtransformer")
                .password("{noop}password")
                .build();
    }

    public static Author buildTestProducerAuthor(Long authorId) {
        return Producer.builder()
                .id(authorId)
                .name("Test Producer")
                .username("testproducer")
                .password("{noop}password")
                .build();
    }

    public static AuthorEntity buildTestTransformerEntity(Long authorId) {
        TransformerEntity result = new TransformerEntity();
        result.setId(authorId);
        result.setName("Test Transformer");
        result.setUsername("testtransformer");
        result.setPassword("{noop}password");
        return result;
    }

    public static AuthorEntity buildTestProducerEntity(Long authorId) {
        ProducerEntity result = new ProducerEntity();
        result.setId(authorId);
        result.setName("Test Producer");
        result.setUsername("testproducer");
        result.setPassword("{noop}password");
        return result;
    }

    public static AuthorEntity buildTestDistributorEntity(Long authorId) {
        DistributorEntity result = new DistributorEntity();
        result.setId(authorId);
        result.setName("Test Distributor");
        result.setUsername("testdistributor");
        result.setPassword("{noop}password");
        return result;
    }
}
