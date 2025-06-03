package it.unicam.cs.agrotrace.util;

import it.unicam.cs.agrotrace.shared.entity.user.CuratorEntity;
import it.unicam.cs.agrotrace.shared.model.user.admin.Curator;

public final class UserTestUtils {

    public static final Long TEST_CURATOR_ID = 1L;
    public static final String TEST_USERNAME_CURATOR = "testCurator";
    public static final String TEST_NAME_CURATOR = "Curator Test";
    public static final String TEST_PASSWORD_CURATOR = "password";

    public static CuratorEntity buildTestCuratorEntity(Long curatorId) {
        CuratorEntity curator = new CuratorEntity();
        curator.setId(curatorId);
        curator.setUsername(TEST_USERNAME_CURATOR);
        curator.setName(TEST_NAME_CURATOR);
        curator.setPassword(TEST_PASSWORD_CURATOR);
        return curator;
    }

    public static Curator buildTestCuratorModel(Long curatorId) {
        return Curator.builder()
                .id(curatorId)
                .username(TEST_USERNAME_CURATOR)
                .name(TEST_NAME_CURATOR)
                .password(TEST_PASSWORD_CURATOR)
                .build();
    }
}
