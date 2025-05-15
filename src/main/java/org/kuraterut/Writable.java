package org.kuraterut;

import java.util.List;

/**
 * Interface for Writing objects
 */
public interface Writable {
    /**
     *
     * @param data      collection of data to write
     * @param fileName  path to file to save
     */
    void writeToFile(List<?> data, String fileName);
}
