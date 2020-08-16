package org.github.hrozhek.reportgenerator.service.input;

import org.github.hrozhek.reportgenerator.dto.EntityDTO;

import java.util.List;

public interface InputFileReaderService {
    List<EntityDTO> readFile();
}
