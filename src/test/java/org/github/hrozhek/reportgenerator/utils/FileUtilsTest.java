package org.github.hrozhek.reportgenerator.utils;

import org.github.hrozhek.reportgenerator.exceptions.FileWithoutExtensionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.github.hrozhek.reportgenerator.utils.FileUtils.doesFileExtensionMatch;
import static org.github.hrozhek.reportgenerator.utils.FileUtils.extractFileExtension;

class FileUtilsTest {
    static private final String FILE_WITH_EXTENSION = "test_file.txt";
    static private final String FILE_WITHOUT_EXTENSION = "test";

    @Test
    void doesFileExtensionMatch_CorrectExtensionPassed_GetTrue() {
        String fileExtension = "txt";
        Assertions.assertTrue(doesFileExtensionMatch(FILE_WITH_EXTENSION, fileExtension));
    }

    @Test
    void doesFileExtensionMatch_IncorrectExtensionPassed_GetFalse() {
        String incorrectFileExtension = "xml";
        Assertions.assertFalse(doesFileExtensionMatch(FILE_WITH_EXTENSION, incorrectFileExtension));
    }

    @Test
    void doesFileExtensionMatch_FileWithoutExtension_GetFalse() {
        String fileExtension = "somewhat";
        Assertions.assertFalse(doesFileExtensionMatch(FILE_WITHOUT_EXTENSION, fileExtension));
    }

    @Test
    void extractFileExtension_PassFileWithExtension_ExtractCorrectly() {
        String actualExtension = "txt";
        Assertions.assertEquals(extractFileExtension(FILE_WITH_EXTENSION), actualExtension);
    }

    @Test
    void extractFileExtension_PassFileWithoutExtension_ThrowException() {
        Executable extractionAttempt = () -> extractFileExtension(FILE_WITHOUT_EXTENSION);
        Assertions.assertThrows(FileWithoutExtensionException.class, extractionAttempt);
    }
}