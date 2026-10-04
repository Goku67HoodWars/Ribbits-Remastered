package com.yungnickyoung.minecraft.ribbits.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Minimal JSON (de)serialization helper for Ribbits' own config/cache files.
 *
 * <p>Drop-in replacement for the slice of YUNG's API {@code io.JSON} that Ribbits used.
 * The field and method names are kept identical so call sites change only their import.
 * The supporters/options payloads are plain data (UUID sets, a boolean), so no custom
 * type adapters are required — Gson handles those natively.
 */
public final class JSON {
    private JSON() {}

    /** Shared Gson instance. Pretty-printed, HTML escaping disabled (matches YUNG's output). */
    public static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    /**
     * Serializes {@code object} to {@code path} as JSON, creating any missing parent dirs.
     */
    public static void createJsonFileFromObject(Path path, Object object) throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.write(path, gson.toJson(object).getBytes());
    }

    /**
     * Deserializes a {@code objectClass} instance from the JSON at {@code path}.
     */
    public static <T> T loadObjectFromJsonFile(Path path, Class<T> objectClass) throws IOException {
        try (Reader reader = Files.newBufferedReader(path)) {
            return gson.fromJson(reader, objectClass);
        }
    }
}
