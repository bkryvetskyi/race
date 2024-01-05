package com.bkryvetskyi.service.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.util.stream.Collectors.toList;

public class DataFileReader implements IFileReader {
    private static final Logger LOGGER = LogManager.getLogger(DataFileReader.class);

    /**
     * Reads lines from the file and returns them as a List of Strings.
     *
     * @param fileName The name of the file to read.
     * @return A List of strings containing the lines read from the file.
     */
    @Override
    public List<String> readFileLines(String fileName) {
        isInValidFilePath(fileName);

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream.collect(toList());
        } catch (IOException e) {
            LOGGER.error("File read error! {}", e.getMessage());
        }

        return new ArrayList<>();
    }

    private void isInValidFilePath(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            LOGGER.error("File path cannot be null, empty, or contain only whitespace.");
        }

        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            LOGGER.error("File does not exist: {}", fileName);
        }

        if (!Files.isRegularFile(path)) {
            LOGGER.error("Path does not lead to a regular file: {}", fileName);
        }
    }
}