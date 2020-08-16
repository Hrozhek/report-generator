package org.github.hrozhek.reportgenerator.service.settings;

import com.google.common.collect.ImmutableMap;
import org.github.hrozhek.reportgenerator.model.PageRepresentation;
import org.github.hrozhek.reportgenerator.model.TableField;
import org.github.hrozhek.reportgenerator.exceptions.SettingsReaderException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class DomXmlSettingsReader extends AbstractXmlSettingsReader {

    public DomXmlSettingsReader(Path settings) {
        super(settings);
    }

    @Override
    protected final PageRepresentation getRepresentationFromXML() {
        Document doc = getDocument();

        Element root = doc.getDocumentElement();
        Node PageRoot = root.getElementsByTagName("page").item(0);
        int width = Integer
                .parseInt(((Element) PageRoot).getElementsByTagName("width").item(0).getTextContent());
        int height = Integer
                .parseInt(((Element) PageRoot).getElementsByTagName("height").item(0).getTextContent());

        Map<Integer, TableField> tableFieldMap = new HashMap<>();
        NodeList columns = ((Element)root.getElementsByTagName("columns").item(0)).getElementsByTagName("column");
        for (int i = 0; i < columns.getLength(); i++) {
            Node column = columns.item(i);

            String name = ((Element) column).getElementsByTagName("title").item(0).getTextContent();
            int columnWidth = Integer
                    .parseInt(((Element) column).getElementsByTagName("width").item(0).getTextContent());

            tableFieldMap.put(i, new TableField(name, columnWidth));
        }

        return new PageRepresentation(width, height, ImmutableMap.copyOf(tableFieldMap));
    }

    private Document getDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            return builder.parse(settings.toFile());
        } catch (Exception e) {
            throw new SettingsReaderException(e);
        }
    }
}

