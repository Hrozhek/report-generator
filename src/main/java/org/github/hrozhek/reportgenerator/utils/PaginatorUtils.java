package org.github.hrozhek.reportgenerator.utils;

import org.github.hrozhek.reportgenerator.model.TableField;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.github.hrozhek.reportgenerator.constants.Constants.HORIZONTAL_BOARDER_ELEMENT;
import static org.github.hrozhek.reportgenerator.constants.Constants.VERTICAL_BOARDER;

public final class PaginatorUtils {

    private PaginatorUtils() {

    }

    public static String createFirstLine(Map<Integer, TableField> fields) {
        StringBuilder result = new StringBuilder();
        result.append(VERTICAL_BOARDER);

        List<String> delimitedFields = new LinkedList<>();
        for (int i = 0; i < fields.size(); i++) {
            TableField tableField = fields.get(i);
            String fieldName = tableField.getName();
            int fieldWidth = tableField.getWidth();

            if (fieldName.length() > fieldWidth) {
                String message = "Incorrect settings: field name %s less than field width %d";
                throw new IllegalStateException(String.format(message, fieldName, fieldWidth));
            } else {
                StringBuilder temp = new StringBuilder(" ");
                delimitedFields.add(temp
                        .append(fieldName)
                        .append(addSpaces(fieldWidth - (fieldName.length()) + 1))
                        .toString());
            }
        }
        delimitedFields.forEach(field -> {
            result.append(field);
            result.append(VERTICAL_BOARDER);
        });

        return result.toString();
    }

    public static StringBuilder addSpaces(int numberOfSpaces) {
        StringBuilder spaceString = new StringBuilder();
        for (int i = 0; i < numberOfSpaces; i++) {
            spaceString.append(" ");
        }
        return spaceString;
    }

    public static String getHorizontalBoarder(int numberOfSymbols) {
        StringBuilder boarder = new StringBuilder();
        for (int i = 0; i < numberOfSymbols; i++) {
            boarder.append(HORIZONTAL_BOARDER_ELEMENT);
        }
        return  boarder.toString();
    }

    public static String convertToBlankLineWithVerticalBorders(String notBlankPage) {
        return notBlankPage.replaceAll("[^| ]", " ");
    }
}
