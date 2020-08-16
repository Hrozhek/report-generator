package org.github.hrozhek.reportgenerator.exceptions;

public class NotAllFilesPassedException extends RuntimeException {
    private final static String MESSAGE = "Not all required files passed: input file: %s, settings file: %s";

    public NotAllFilesPassedException(String input, String settings) {
        super(String.format(MESSAGE, input, settings));
    }
}
