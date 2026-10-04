package com.yungnickyoung.minecraft.ribbits.module;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.config.RibbitsConfig;
import com.yungnickyoung.minecraft.ribbits.platform.PlatformHelper;
import com.yungnickyoung.minecraft.ribbits.util.JsonIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Loads and stores {@link RibbitsConfig}. The file is read once at start-up and written back
 * whenever the config screen saves.
 */
public class ConfigModule {
    private static final String FILE_NAME = "ribbits.json";

    private static RibbitsConfig config = new RibbitsConfig();

    public static void init() {
        Path path = getConfigPath();

        if (!Files.exists(path)) {
            save();
            return;
        }

        try {
            RibbitsConfig loaded = JsonIO.readObjectFromFile(path, RibbitsConfig.class);
            if (loaded != null) {
                // A partially written file leaves the missing sections null, so fill those back in.
                if (loaded.general == null) loaded.general = new RibbitsConfig.General();
                if (loaded.network == null) loaded.network = new RibbitsConfig.Network();
                config = loaded;
            }
        } catch (IOException | RuntimeException e) {
            RibbitsCommon.LOGGER.error("Error loading {}: {}. Using default configuration...", FILE_NAME, e.toString());
        }
    }

    public static void save() {
        try {
            JsonIO.writeObjectToFile(getConfigPath(), config);
        } catch (IOException e) {
            RibbitsCommon.LOGGER.error("Error saving {}: {}", FILE_NAME, e.toString());
        }
    }

    public static RibbitsConfig getConfig() {
        return config;
    }

    public static boolean prideFlagAllYear() {
        return config.general.prideFlagAllYear;
    }

    private static Path getConfigPath() {
        return PlatformHelper.getConfigFolder().resolve(FILE_NAME);
    }
}
