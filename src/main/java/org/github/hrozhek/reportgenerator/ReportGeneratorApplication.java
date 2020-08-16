package org.github.hrozhek.reportgenerator;

import org.github.hrozhek.reportgenerator.data.input.InputDataReaderImpl;
import org.github.hrozhek.reportgenerator.data.output.OutputDataWriterImpl;
import org.github.hrozhek.reportgenerator.dto.FilesetDTO;
import org.github.hrozhek.reportgenerator.exceptions.DuplicateFileExtension;
import org.github.hrozhek.reportgenerator.exceptions.NotAllFilesPassedException;
import org.github.hrozhek.reportgenerator.model.Fileset;
import org.github.hrozhek.reportgenerator.model.PageRepresentation;
import org.github.hrozhek.reportgenerator.service.input.InputFileReaderService;
import org.github.hrozhek.reportgenerator.service.input.InputFileReaderServiceImpl;
import org.github.hrozhek.reportgenerator.service.output.OutputFileWriterService;
import org.github.hrozhek.reportgenerator.service.output.OutputFileWriterServiceImpl;
import org.github.hrozhek.reportgenerator.service.settings.DomXmlSettingsReader;
import org.github.hrozhek.reportgenerator.service.settings.SettingsReader;

import java.util.HashMap;
import java.util.Map;

import static org.github.hrozhek.reportgenerator.utils.FileUtils.extractFileExtension;

public class ReportGeneratorApplication {
    private final static String DEFAULT_OUTPUT_FILE = "src/main/resources/output.txt";
    private final static String INPUT_FILE_EXTENSION = "tsv";
    private final static String OUTPUT_FILE_EXTENSION = "txt";
    private final static String SETTINGS_FILE_EXTENSION = "xml";


    public static void main(String[] args) {
        Fileset fileset = Fileset.initFromDTO(prepareFilesetFromArgs(args));

        SettingsReader settingsReader = new DomXmlSettingsReader(fileset.getSettings());
        PageRepresentation page = settingsReader.getPage();

        InputFileReaderService inputReader = new InputFileReaderServiceImpl
                (new InputDataReaderImpl(fileset.getInput()), page);
        OutputFileWriterService outputWriter = new OutputFileWriterServiceImpl
                (new OutputDataWriterImpl(fileset.getOutput()), page);

        outputWriter.writeObjects(inputReader.readFile());
    }

    private static FilesetDTO prepareFilesetFromArgs(String[] args) {
        Map<String, String> mappedFilenames = resolveArgs(args);
        String input = mappedFilenames.get(INPUT_FILE_EXTENSION);
        String output = mappedFilenames.get(OUTPUT_FILE_EXTENSION);
        String settings = mappedFilenames.get(SETTINGS_FILE_EXTENSION);

        if (input == null || settings == null) throw new NotAllFilesPassedException(input, settings);

        if (output == null) output = DEFAULT_OUTPUT_FILE;

        return FilesetDTO.builder()
                .inputFile(input)
                .outputFile(output)
                .settingsFile(settings)
                .build();
    }

    private static Map<String, String> resolveArgs(String[] args) {
        Map<String, String> mappedFilenames = new HashMap<>();

        for (String filename : args) {
            String fileExtension = extractFileExtension(filename);
            if (mappedFilenames.put(fileExtension, filename) != null) {
                throw new DuplicateFileExtension(filename);
            }
        }
        return mappedFilenames;
    }

}
