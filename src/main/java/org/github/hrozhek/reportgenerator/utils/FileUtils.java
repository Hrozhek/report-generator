package org.github.hrozhek.reportgenerator.utils;

import org.github.hrozhek.reportgenerator.exceptions.FileWithoutExtensionException;

import java.util.Optional;

public final class FileUtils {
    public static boolean doesFileExtensionMatch(String filename, String extension) {
        return !filename.equals(extension) && Optional.ofNullable(filename)
                .map(f -> f.substring(filename.lastIndexOf(".") + 1)).get()
                .equals(extension);
    }

    public static String extractFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                .orElseThrow(() -> new FileWithoutExtensionException(filename));
    }
}
