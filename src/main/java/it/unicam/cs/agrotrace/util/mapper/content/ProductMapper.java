package it.unicam.cs.agrotrace.util.mapper.content;

import it.unicam.cs.agrotrace.rest.view.ProductView;
import it.unicam.cs.agrotrace.shared.entity.content.ProductEntity;
import it.unicam.cs.agrotrace.shared.model.content.Product;
import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;
import it.unicam.cs.agrotrace.util.mapper.user.author.AuthorMapper;

final class ProductMapper extends AbstractContentMapper<ProductView, Product, ProductEntity> {

    ProductMapper(AuthorMapper authorMapper) {
        super(authorMapper);
    }

    @Override
    public ProductEntity entityFromModel(Product model) {
        ProductEntity entity = new ProductEntity();
        entity.setId(model.getId());
        entity.setTitle(model.getTitle());
        entity.setDescription(model.getDescription());
        entity.setValidationStatus(model.getValidationStatus().name());
        entity.setAuthor(authorMapper.entityFromModel(model.getAuthor()));
        return entity;
    }

    @Override
    public Product modelFromEntity(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .validationStatus(ValidationStatus.valueOf(entity.getValidationStatus()))
                .author(authorMapper.modelFromEntity(entity.getAuthor()))
                .build();
    }

    @Override
    public ProductView viewFromModel(Product model) {
        ProductView view = new ProductView();
        view.setId(model.getId().toString());
        view.setTitle(model.getTitle());
        view.setDescription(model.getDescription());
        view.setValidationStatus(model.getValidationStatus().name());
        view.setAuthorId(model.getAuthor().getId());
        return view;
    }
}
