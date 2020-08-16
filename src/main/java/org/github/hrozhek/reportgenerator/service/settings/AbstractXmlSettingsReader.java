package org.github.hrozhek.reportgenerator.service.settings;

import lombok.RequiredArgsConstructor;
import org.github.hrozhek.reportgenerator.model.PageRepresentation;
import java.nio.file.Path;
import java.util.Objects;

@RequiredArgsConstructor
public abstract class AbstractXmlSettingsReader implements SettingsReader {
    protected final Path settings;

    protected PageRepresentation page;

    @Override
    public PageRepresentation getPage() {
        if (Objects.isNull(page)) {
            page = getRepresentationFromXML();
        }
        return page;
    }

    protected abstract PageRepresentation getRepresentationFromXML();

}
