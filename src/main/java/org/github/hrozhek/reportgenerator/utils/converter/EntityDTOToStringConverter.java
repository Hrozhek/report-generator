package org.github.hrozhek.reportgenerator.utils.converter;

import org.github.hrozhek.reportgenerator.dto.EntityDTO;
import org.github.hrozhek.reportgenerator.model.TableField;

import java.util.*;

import static org.github.hrozhek.reportgenerator.constants.Constants.*;
import static org.github.hrozhek.reportgenerator.utils.PaginatorUtils.addSpaces;


public final class EntityDTOToStringConverter {
    private EntityDTOToStringConverter() {
    }

    public static LinkedList<String> convert(EntityDTO dto, Map<Integer, TableField> fields) {
        List<String> rawValues = dto.getFields();
        List<List<String>> listOfList = new ArrayList<>();
        int maxSize = 0;
        for (Integer key : fields.keySet()) {
            List<String> temp = splitByWidthIfNecessary(rawValues.get(key), fields.get(key).getWidth());
            listOfList.add(temp);
            if (temp.size() > maxSize) {
                maxSize = temp.size();
            }
        }

        for (int i = 0; i < listOfList.size(); i++) {
            List<String> list = listOfList.get(i);
            int width = fields.get(i).getWidth();
            if (list.size() < maxSize) {
                stringListAlign(list, maxSize, width);
            }
        }
        return convertListOfFieldsToLinesList(listOfList);
    }

    private static List<String> splitByWidthIfNecessary(String value, int width) {
        List<String> stringLinesFromValue = new LinkedList<>();
        //simple case
        if (value.length() < width) {
            stringLinesFromValue.add(" " + value + addSpaces(width - value.length() + 1));
        } else {
            List<WordAndDelimiter> subWords = split(value);
            stringLinesFromValue.addAll(splitWordAndDelimitersToStrings(alsoSplitByWidth(subWords, width), width));
        }
        return stringLinesFromValue;

    }

    private static List<WordAndDelimiter> alsoSplitByWidth(List<WordAndDelimiter> subWords, int width) {
        List<WordAndDelimiter> toReturn = new LinkedList<>();
        for (WordAndDelimiter subWord: subWords) {
            String word = subWord.getWord();
            if (word.length() > width) {
                List<WordAndDelimiter> temp = new LinkedList<>();
                while (word.length() > width) {
                    int size = word.length();
                    temp.add(new WordAndDelimiter(new StringBuilder(word.substring(0, width))," "));
                    word = word.substring(width, size);
                }
                temp.add(new WordAndDelimiter(new StringBuilder(word), " ")); //add last
                toReturn.addAll(temp);
            } else {
                toReturn.add(subWord);
            }
        }
        return toReturn;
    }

    private static List<WordAndDelimiter> split(String value) {
        List<WordAndDelimiter> splittedWords = new LinkedList<>();
        String pattern = INSIDE_WORD_PATTERN;
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            String symbol = new String(new char[]{value.charAt(i)});
            if (symbol.matches(pattern)) {
                temp.append(symbol);
            } else {
                splittedWords.add(new WordAndDelimiter(temp, new String(new char[]{value.charAt(i)})));
                temp = new StringBuilder();
            }
        }
        splittedWords.add(new WordAndDelimiter(temp, " "));
        return splittedWords;
    }

    private static List<String> splitWordAndDelimitersToStrings(List<WordAndDelimiter> from, int width) {
        List<String> to = new LinkedList<>();
        StringBuilder temp = new StringBuilder(" ");
        String tempDelimiter = "";
        for (int i = 0; i < from.size(); i++) {
            WordAndDelimiter wordAndDelimiter = from.get(i);
            if ((temp.length() + wordAndDelimiter.getWord().length()) <= width + 1) {
                temp.append(wordAndDelimiter.getWord());
                if (!wordAndDelimiter.getDelimiter().isPresent()) {
                    temp.append(" ");
                } else {
                    if (temp.length() < width + 1) {
                        temp.append(wordAndDelimiter.getDelimiter().get());
                    } else {
                        //flush temp and get new
                        to.add(temp.toString() + addSpaces(width - temp.length() + 2));
                        temp = new StringBuilder(" " + wordAndDelimiter.getDelimiter().get());
                    }
                }
            } else {
                to.add(temp.toString() + addSpaces(width - temp.length() + 2));
                temp = new StringBuilder(" ").append(wordAndDelimiter.getWord());
                tempDelimiter = wordAndDelimiter.getDelimiter().orElse("");
                if (temp.length() < width + 1) {
                    temp.append(tempDelimiter);
                } else {
                    //flush temp and get new
                    to.add(temp.toString() + addSpaces(width - temp.length() + 2));
                    temp = new StringBuilder(" " + tempDelimiter);
                }
            }
        }
        to.add(temp.toString() + addSpaces(width - temp.length() + 2));
        return to;
    }

    private static LinkedList<String> convertListOfFieldsToLinesList(List<List<String>> listOfList) {
        LinkedList<String> result = new LinkedList<>();
        for (int i = 0; i < listOfList.get(0).size(); i++) {
            StringBuilder line = new StringBuilder(VERTICAL_BOARDER);
            for (int j = 0; j < listOfList.size(); j++) {
                line.append(listOfList.get(j).get(i)).append(VERTICAL_BOARDER);
            }
            result.add(line.toString());
        }

        return result;
    }

    private static void stringListAlign(List<String> notAligned, int toSize, int width) {
        while (notAligned.size() != toSize) {
            notAligned.add(addSpaces(width + 2).toString());
        }
    }

    private static class WordAndDelimiter {
        public WordAndDelimiter(StringBuilder word, String delimiter) {
            this.word = word.toString();
            if (delimiter.equals(" ")) {
                this.delimiter = Optional.empty();
            } else {
                this.delimiter = Optional.of(delimiter);
            }
        }

        private String word;
        private Optional<String> delimiter;

        public String getWord() {
            return word;
        }

        public Optional<String> getDelimiter() {
            return delimiter;
        }
    }
}
