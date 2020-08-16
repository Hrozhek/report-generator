package org.github.hrozhek.reportgenerator.service.input;

import lombok.RequiredArgsConstructor;
import org.github.hrozhek.reportgenerator.data.input.InputDataReader;
import org.github.hrozhek.reportgenerator.dto.EntityDTO;
import org.github.hrozhek.reportgenerator.model.PageRepresentation;
import org.github.hrozhek.reportgenerator.utils.converter.StringToEntityDTOConverter;

import java.util.List;
import java.util.stream.Collectors;

import static org.github.hrozhek.reportgenerator.utils.converter.StringToEntityDTOConverter.getNumberOfFields;

@RequiredArgsConstructor
public class InputFileReaderServiceImpl implements InputFileReaderService {
    private final InputDataReader reader;
    private final PageRepresentation page;

    private List<EntityDTO> dtoList;

    @Override
    public List<EntityDTO> readFile() {
        List<String> stringRepresentations = reader.readEntities();

        stringRepresentations.stream()
                .filter(stringRepr -> getNumberOfFields(stringRepr) != page.getNumberOfFields())
                .map(incorrect -> "Incorrectly read string from file:\n" + incorrect)
                .forEach(System.out::println);

        return stringRepresentations.stream()
                .filter(stringRepr -> getNumberOfFields(stringRepr) == page.getNumberOfFields())
                .map(StringToEntityDTOConverter::convert)
                .collect(Collectors.toList());
    }

}
