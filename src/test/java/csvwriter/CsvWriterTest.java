package csvwriter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.kuraterut.CsvWriter;
import org.kuraterut.model.Months;
import org.kuraterut.model.Person;
import org.kuraterut.model.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvWriterTest {

    @TempDir
    Path tempDir;

    @Test
    void testWritePersonToCsv() throws IOException {
        CsvWriter writer = new CsvWriter();
        List<Person> people = Arrays.asList(
                Person.builder()
                        .firstName("TestName")
                        .lastName("TestLastName")
                        .dayOfBirth(6)
                        .monthOfBirth(Months.JUNE)
                        .yearOfBirth(2005)
                        .build()
        );

        Path filePath = tempDir.resolve("people.csv");
        writer.writeToFile(people, filePath.toString());
        List<String> lines = Files.readAllLines(filePath);


        assertEquals(2, lines.size());
        assertEquals("FirstName,LastName,BirthDay,BirthMonth,BirthYear", lines.get(0));
        assertEquals("TestName,TestLastName,6,JUNE,2005", lines.get(1));
    }

    @Test
    void testWriteStudentToCsv() throws IOException {
        CsvWriter writer = new CsvWriter();
        List<Student> students = Arrays.asList(
                Student.builder()
                        .name("Student1")
                        .score(Arrays.asList("A", "B", "F"))
                        .build()
        );

        Path filePath = tempDir.resolve("students.csv");
        writer.writeToFile(students, filePath.toString());

        List<String> lines = Files.readAllLines(filePath);
        assertEquals(2, lines.size());
        assertEquals("Name,ScoreList", lines.get(0));
        assertEquals("Student1,A|B|F", lines.get(1));
    }

    @Test
    void testFieldOrderRespected() throws IOException {
        CsvWriter writer = new CsvWriter();
        List<Person> people = List.of(
                Person.builder()
                        .firstName("TestName")
                        .lastName("TestLastName")
                        .dayOfBirth(6)
                        .monthOfBirth(Months.JUNE)
                        .yearOfBirth(2005)
                        .build()
        );

        Path filePath = tempDir.resolve("order_test.csv");
        writer.writeToFile(people, filePath.toString());

        String header = Files.readAllLines(filePath).get(0);
        assertEquals("FirstName,LastName,BirthDay,BirthMonth,BirthYear", header);
    }
}