package org.github.hrozhek.reportgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class FilesetDTO {
    private String inputFile;
    private String outputFile;
    private String settingsFile;
}
