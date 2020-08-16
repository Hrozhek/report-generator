package org.github.hrozhek.reportgenerator.model;

import lombok.Getter;
import org.github.hrozhek.reportgenerator.dto.FilesetDTO;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class Fileset {
    private Path input;
    private Path output;
    private Path settings;

    private Fileset(FilesetDTO dto) {
        input = Paths.get(dto.getInputFile());
        output = Paths.get(dto.getOutputFile());
        settings = Paths.get(dto.getSettingsFile());
    }

    public static Fileset initFromDTO(FilesetDTO dto) {
        Fileset fileset = new Fileset(dto);
        fileset.tryAccess();
        return fileset;
    }

    private void tryAccess() {
        //check if all files are available. If not, throw exceptions in runtime.
        // Check if output is empty and rewrite it by request
    }
}
