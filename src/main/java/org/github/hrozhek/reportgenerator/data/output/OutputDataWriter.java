package org.github.hrozhek.reportgenerator.data.output;

import java.util.List;

public interface OutputDataWriter {
    void writeLines(List<String> lines);
}
