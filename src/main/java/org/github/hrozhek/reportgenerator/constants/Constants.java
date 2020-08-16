package org.github.hrozhek.reportgenerator.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class Constants {
    public final static String VERTICAL_BOARDER = "|";
    public final static String HORIZONTAL_BOARDER_ELEMENT = "-";
    public final static Charset CHARSET = StandardCharsets.UTF_16;
    public final static String INSIDE_WORD_PATTERN = "[а-яА-ЯёЁa-zA-Z0-9]";
    public final static String OUTSIDE_WORD_PATTERN = "^[а-яА-ЯёЁa-zA-Z0-9]";

    private Constants() {}
}
