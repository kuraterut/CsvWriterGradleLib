package org.kuraterut.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.kuraterut.CsvColumn;

@Data
@Builder
@AllArgsConstructor
public class Person {
    @CsvColumn(name = "FirstName", order = 1)
    private String firstName;
    @CsvColumn(name = "LastName", order = 2)
    private String lastName;
    @CsvColumn(name = "BirthDay", order = 3)
    private int dayOfBirth;
    @CsvColumn(name = "BirthMonth", order = 4)
    private Months monthOfBirth;
    @CsvColumn(name = "BirthYear", order = 5)
    private int yearOfBirth;

}
