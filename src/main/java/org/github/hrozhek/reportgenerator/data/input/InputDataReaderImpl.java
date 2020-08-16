package org.github.hrozhek.reportgenerator.data.input;

import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import static org.github.hrozhek.reportgenerator.constants.Constants.CHARSET;

@RequiredArgsConstructor
public class InputDataReaderImpl implements InputDataReader {
    private final Path input;
    private List<String> entities;

    @Override
    public List<String> readEntities() {
        entities = new LinkedList<>();
        try(BufferedReader reader = new BufferedReader
                (new InputStreamReader(
                        new FileInputStream(input.toFile()), CHARSET))) {
            String line;
            while ((line = reader.readLine()) != null ) entities.add(line);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entities;
    }
}
