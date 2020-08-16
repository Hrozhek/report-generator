package org.github.hrozhek.reportgenerator.service.output;

import lombok.RequiredArgsConstructor;
import org.github.hrozhek.reportgenerator.data.output.OutputDataWriter;
import org.github.hrozhek.reportgenerator.dto.EntityDTO;
import org.github.hrozhek.reportgenerator.model.PageRepresentation;
import org.github.hrozhek.reportgenerator.model.TableField;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.github.hrozhek.reportgenerator.utils.PaginatorUtils.*;
import static org.github.hrozhek.reportgenerator.utils.converter.EntityDTOToStringConverter.convert;

@RequiredArgsConstructor
public class OutputFileWriterServiceImpl implements OutputFileWriterService {
    private final OutputDataWriter writer;
    private final PageRepresentation pageView;

    @Override
    public void writeObjects(List<EntityDTO> dto) {
        Paginator paginator = new Paginator();
        dto.forEach(paginator::addToPage);
        writer.writeLines(paginator.getAllLines());
    }

    private class Paginator {
        private List<String> pages;
        private Map<Integer, TableField> fields;

        private String firstLine;
        private String blankLine;
        private String horizontalBoarder;
        private int height;

        private Paginator() {
            pages = new LinkedList<>();
            fields = pageView.getTableFields();
            firstLine = createFirstLine(fields);
            blankLine = convertToBlankLineWithVerticalBorders(firstLine);
            horizontalBoarder = getHorizontalBoarder(pageView.getWidth());
            height = pageView.getHeight();

            pages.add(firstLine);
            pages.add(horizontalBoarder);
        }

        private void addToPage(EntityDTO entityDTO) {
            if (pages.size() % height == 0) {
                createNewPage();
            }

            LinkedList<String> convertedEntity = convert(entityDTO, fields);

            while (!convertedEntity.isEmpty()) {
                writeLinesToPage(convertedEntity);

                if (!convertedEntity.isEmpty()) {
                    createNewPage();
                }
            }
        }

        private void writeLinesToPage(LinkedList<String> lines) {
            for(;lines.size() != 0;) {
                pages.add(lines.remove(0));
                if (pages.size() % height == 0) {
                    break;
                }
                if (lines.size() == 0) {
                    pages.add(horizontalBoarder);
                }
            }
        }

        private void createNewPage() {
            pages.add(firstLine);
            pages.add(horizontalBoarder);
        }

        private List<String> getAllLines() {
            fillLastPageAndAddDelimiter();
            return pages;
        }

        private void fillLastPageAndAddDelimiter() {
            while (pages.size() % height != 0) {
                pages.add(blankLine);
            }
            List<String> readyList = new LinkedList<>();
            for (int i = 0; i < pages.size(); i ++) {
                readyList.add(pages.get(i));
                if ( i % height == (height - 1) && i != pages.size() - 1) {
                    readyList.add("~");
                }
            }
            pages = readyList;
        }
    }
}
