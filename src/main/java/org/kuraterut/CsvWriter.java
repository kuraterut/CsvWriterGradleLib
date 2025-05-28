package org.kuraterut;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CsvWriter implements Writable{
    /**
     *  Method for writing List of objects to file.
     * @param data      collection of data to write
     * @param fileName  path to CSV file to save
     */
    @Override
    public void writeToFile(List<?> data, String fileName) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Data list cannot be null or empty");
        }
        if (fileName == null || fileName.isEmpty() || !fileName.trim().endsWith(".csv")) {
            throw new IllegalArgumentException("File name must have CSV format");
        }

        Class<?> itemClass = data.get(0).getClass();
        try (FileWriter writer = new FileWriter(fileName)) {
            writeHeader(writer, itemClass);

            for (Object item : data) {
                writeRow(writer, item);
            }
        }
        catch (IOException | IllegalAccessException e) {
            throw new RuntimeException("Error writing to CSV file", e);
        }
    }

    /**
     * Method which write Headers to file. Headers are taken from the annotations or from field name.
     * @param writer        FileWriter for our CSV File.
     * @param itemClass     Class info of our data Class.
     * @throws IOException  problems with writing to file.
     */
    private void writeHeader(FileWriter writer, Class<?> itemClass) throws IOException {
        List<Field> fields = getAnnotatedFields(itemClass);
        for (int i = 0; i < fields.size(); i++) {
            CsvColumn annotation = fields.get(i).getAnnotation(CsvColumn.class);
            String columnName = annotation.name().isEmpty() ?
                    fields.get(i).getName() : annotation.name();
            writer.write(columnName);
            if (i < fields.size() - 1) {
                writer.write(",");
            }
        }
        writer.write("\n");
    }

    /**
     * Method which write Object info to file.
     * @param writer                    FileWriter for our CSV File.
     * @param item                      Our Object which we need to write to CSV File
     * @throws IOException              Problems with writing to file.
     * @throws IllegalAccessException   Problems with writing to file.
     */
    private void writeRow(FileWriter writer, Object item) throws IOException, IllegalAccessException {
        List<Field> fields = getAnnotatedFields(item.getClass());
        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).setAccessible(true);
            Object value = fields.get(i).get(item);

            if (value instanceof List) {
                writer.write(String.join("|", (List<String>) value));
            }
            else {
                writer.write(value != null ? value.toString() : "");
            }

            if (i < fields.size() - 1) {
                writer.write(",");
            }
        }
        writer.write("\n");
    }

    /**
     * Method for searching Annotated fields in given class.
     * @param itemClass         Class info
     * @return                  List of Annotated Fields
     */
    private List<Field> getAnnotatedFields(Class<?> itemClass) {
        return Arrays.stream(itemClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(CsvColumn.class))
                .sorted(Comparator.comparingInt(field -> field.getAnnotation(CsvColumn.class).order()))
                .collect(Collectors.toList());
    }
}
