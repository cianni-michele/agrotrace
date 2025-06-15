package it.unicam.cs.agrotrace.util.mapper.file;

import it.unicam.cs.agrotrace.shared.entity.file.CertificationEntity;
import it.unicam.cs.agrotrace.shared.model.file.Certification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static it.unicam.cs.agrotrace.util.UploadedFileTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class CertificationMapperTest {

    private CertificationMapper certificationMapper;

    @BeforeEach
    void setUp() {
        certificationMapper = new CertificationMapper();
    }

    @Test
    void entityFromModel() {
        Certification model = buildTestCertificationUploadedFile(null);

        CertificationEntity entity = certificationMapper.entityFromModel(model);

        assertNotNull(entity);
        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getType().name(), entity.getType());
        assertEquals(model.getPath().toString(), entity.getPath());
    }

    @Test
    void modelFromEntity() {
        CertificationEntity entity = buildTestCertificationEntity(TEST_FILE_ID);

        Certification model = certificationMapper.modelFromEntity(entity);

        assertNotNull(model);
        assertEquals(entity.getId(), model.getId());
        assertEquals(Certification.Type.valueOf(entity.getType()), model.getType());
        assertEquals(Path.of(entity.getPath()), model.getPath());
    }
}