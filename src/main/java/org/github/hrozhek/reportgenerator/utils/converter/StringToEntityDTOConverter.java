package org.github.hrozhek.reportgenerator.utils.converter;

import org.github.hrozhek.reportgenerator.dto.EntityDTO;

import java.util.ArrayList;
import java.util.List;

public final class StringToEntityDTOConverter {
    private StringToEntityDTOConverter() {}

    public static EntityDTO convert(String string) {
        return new EntityDTO(splitByTab(string));
    }

    public static int getNumberOfFields(String string) {
        return splitByTab(string).size();
    }

    private static List<String> splitByTab(String string) {
        char delimiter = '\t';
        int lastCharPosition = string.length() - 1;

        List<String> substrings = new ArrayList<>();

        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == delimiter) {
                substrings.add(temp.toString());
                temp = new StringBuilder();
            } else {
                temp.append(string.charAt(i));
            } if (i == lastCharPosition) {
                substrings.add(temp.toString());
            }
        }

        return substrings;
    }
}
