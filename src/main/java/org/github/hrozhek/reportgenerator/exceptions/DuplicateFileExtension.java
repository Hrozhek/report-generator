package org.github.hrozhek.reportgenerator.exceptions;

public class DuplicateFileExtension extends RuntimeException {
    public DuplicateFileExtension(String filename) {
        super("Found duplicate file: " + filename);
    }
}
