package org.github.hrozhek.reportgenerator.model;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.Map;

@Getter
public class PageRepresentation {
    private int width;
    private int height;
    private Map<Integer, TableField> tableFields;

    public PageRepresentation(int width, int height, Map<Integer, TableField> tableFields) {
        this.width = width;
        this.height = height;
        this.tableFields = ImmutableMap.copyOf(tableFields);
    }

    public Integer getNumberOfFields() {
        return tableFields.size();
    }
}
