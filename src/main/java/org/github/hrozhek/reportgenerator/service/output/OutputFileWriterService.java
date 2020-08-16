package org.github.hrozhek.reportgenerator.service.output;

import org.github.hrozhek.reportgenerator.dto.EntityDTO;

import java.util.List;

public interface OutputFileWriterService {
    void writeObjects(List<EntityDTO> dto);
}
