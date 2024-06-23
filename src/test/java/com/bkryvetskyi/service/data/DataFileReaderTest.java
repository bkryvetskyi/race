package com.bkryvetskyi.service.data;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataFileReaderTest {

    @Test
    void readFileLines() {

    }
    @Test
    void testReadFileLinesPositiveScenario() {
        DataFileReader dataFileReader = new DataFileReader();
        String fileName = "start.log";
        List<String> lines = dataFileReader.readFileLines(fileName);

        assertNotNull(lines);
        assertFalse(lines.isEmpty());
    }

    @Test
    void testReadFileLinesWithNullFileName() {
        DataFileReader dataFileReader = new DataFileReader();

        assertThrows(IllegalArgumentException.class, () -> {
            dataFileReader.readFileLines(null);
        });
    }

    @Test
    void testReadFileLinesWithEmptyFileName() {
        DataFileReader dataFileReader = new DataFileReader();

        assertThrows(IllegalArgumentException.class, () -> {
            dataFileReader.readFileLines("\n");
        });
    }

    @Test
    void testReadFileLinesWithNonExistentFile() {
        DataFileReader dataFileReader = new DataFileReader();
        String nonExistentFileName = "nonExistentFile.txt";

        assertThrows(IllegalArgumentException.class, () -> {
            dataFileReader.readFileLines(nonExistentFileName);
        });
    }

    @Test
    void testReadFileLinesWithInvalidFilePath() {
        DataFileReader dataFileReader = new DataFileReader();
        String folderPath = "src/test/resources/folder";

        assertThrows(IllegalArgumentException.class, () -> {
            dataFileReader.readFileLines(folderPath);
        });
    }
}