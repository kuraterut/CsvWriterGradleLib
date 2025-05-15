package org.kuraterut;

import org.kuraterut.model.Months;
import org.kuraterut.model.Person;
import org.kuraterut.model.Student;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CsvWriter csvWriter = new CsvWriter();

        List<Person> people = List.of(
                Person.builder()
                        .firstName("Ilia")
                        .lastName("Kurylin")
                        .dayOfBirth(6)
                        .monthOfBirth(Months.JUNE)
                        .yearOfBirth(2005)
                        .build()
        );

        List<Student> students = List.of(
                Student.builder()
                        .name("Vanya")
                        .score(List.of("A", "C", "B"))
                        .build(),
                Student.builder()
                        .name("Kevin")
                        .score(List.of("F", "F", "F", "C"))
                        .build()
        );

        csvWriter.writeToFile(people, "csvFiles/people.csv");
        csvWriter.writeToFile(students, "csvFiles/students.csv");

        System.out.println("CSV files created successfully!");
    }
}