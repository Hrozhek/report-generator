package org.github.hrozhek.reportgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class EntityDTO {
    private final List<String> fields;
}
