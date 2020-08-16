package org.github.hrozhek.reportgenerator.data.output;

import lombok.RequiredArgsConstructor;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.List;

import static org.github.hrozhek.reportgenerator.constants.Constants.CHARSET;

@RequiredArgsConstructor
public class OutputDataWriterImpl implements OutputDataWriter {
    private final Path output;

    @Override
    public void writeLines(List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter
                (new OutputStreamWriter(
                        new FileOutputStream(output.toFile()), CHARSET))) {
            for(String line: lines) {
                writer.write(line);
                writer.newLine();
                writer.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
