package org.github.hrozhek.reportgenerator.exceptions;

public class SettingsReaderException extends RuntimeException {
    private static final String MESSAGE = "Error occurred while reading file";
    public SettingsReaderException(Exception e) {
        super(MESSAGE);
        super.addSuppressed(e);
    }
}
