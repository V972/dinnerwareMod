package net.v972.dinnerware.datagen;

import java.nio.file.Path;

public final class DinnerwareDatagenPaths {
    public static final String COMMON_OUTPUT_PROPERTY = "dinnerware.datagen.commonOutput";

    private DinnerwareDatagenPaths() {
    }

    public static Path commonOutput() {
        String configuredPath = System.getProperty(COMMON_OUTPUT_PROPERTY);

        if (configuredPath == null || configuredPath.isBlank()) {
            throw new IllegalStateException(
                "Missing datagen system property: " + COMMON_OUTPUT_PROPERTY
            );
        }

        return Path.of(configuredPath)
            .toAbsolutePath()
            .normalize();
    }
}