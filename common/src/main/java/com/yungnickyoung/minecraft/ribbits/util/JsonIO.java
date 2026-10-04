package com.yungnickyoung.minecraft.ribbits.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Reading and writing the small JSON files this mod keeps in the config folder.
 */
public final class JsonIO {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private JsonIO() {
    }

    /**
     * Writes {@code object} to {@code path}, creating any missing parent directories.
     */
    public static void writeObjectToFile(Path path, Object object) throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            GSON.toJson(object, writer);
        }
    }

    /**
     * Reads a {@code clazz} instance from {@code path}.
     */
    public static <T> T readObjectFromFile(Path path, Class<T> clazz) throws IOException {
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return GSON.fromJson(reader, clazz);
        }
    }
}
