package org.kuraterut.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.kuraterut.CsvColumn;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Student {
    @CsvColumn(name = "Name", order = 1)
    private String name;
    @CsvColumn(name = "ScoreList", order = 2)
    private List<String> score;
}