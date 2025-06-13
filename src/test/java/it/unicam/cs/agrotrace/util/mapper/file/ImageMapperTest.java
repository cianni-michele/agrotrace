package it.unicam.cs.agrotrace.util.mapper.file;

import it.unicam.cs.agrotrace.shared.entity.file.ImageEntity;
import it.unicam.cs.agrotrace.shared.model.file.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static it.unicam.cs.agrotrace.util.UploadedFileTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class ImageMapperTest {

    private ImageMapper imageMapper;

    @BeforeEach
    void setUp() {
        imageMapper = new ImageMapper();
    }

    @Test
    void entityFromModel() {
        Image model = buildTestImageUploadedFile();

        ImageEntity entity = imageMapper.entityFromModel(model);

        assertNotNull(entity);
        assertNull(entity.getId());
        assertEquals(model.getPath().toString(), entity.getPath());
        assertEquals(model.getDescription(), entity.getDescription());
    }

    @Test
    void modelFromEntity() {
        ImageEntity entity = buildTestImageEntity();

        Image model = imageMapper.modelFromEntity(entity);

        assertNotNull(model);
        assertEquals(entity.getId(), model.getId());
        assertEquals(Path.of(entity.getPath()), model.getPath());
        assertEquals(entity.getDescription(), model.getDescription());
    }
}