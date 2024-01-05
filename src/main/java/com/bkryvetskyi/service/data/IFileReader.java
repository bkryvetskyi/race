package com.bkryvetskyi.service.data;

import java.util.List;

/**
 * An interface representing a file reader, providing methods to read lines from a file.
 */
public interface IFileReader {
    /**
     * Reads lines from the given file and returns them as a List of Strings.
     *
     * @param fileName The name of the file to read.
     * @return A List of strings containing the lines read from the file.
     */
    List<String> readFileLines(String fileName);
}
