package com.bkryvetskyi.service.data;

import com.bkryvetskyi.model.LapTime;
import com.bkryvetskyi.model.Racer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RaceDataReaderTest {

    @TempDir
     Path tempDir;

    private RaceDataReader raceDataReader;
    private String testRacersFilePath;
    private String testStartLogFilePath;
    private String testEndLogFilePath;

    @BeforeEach
    void setUp() {
        raceDataReader = new RaceDataReader();
        testRacersFilePath = tempDir.resolve("test_abbreviations.txt").toString();
        testStartLogFilePath = tempDir.resolve("test_start.log").toString();
        testEndLogFilePath = tempDir.resolve("test_end.log").toString();
    }

    @Test
    void readRacersFromFile() throws IOException {
        // Create a test racers file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testRacersFilePath))) {
            writer.write("R1_Racer 1_Team A\n");
            writer.write("R2_Racer 2_Team B\n");
        }

        List<Racer> racers = raceDataReader.readRacersFromFile(testRacersFilePath);
        assertEquals(2, racers.size());

        // Verify racer details
        Racer racer1 = racers.get(0);
        assertEquals("R1", racer1.getAbbreviation());
        assertEquals("Racer 1", racer1.getRacerName());
        assertEquals("Team A", racer1.getTeam());

        Racer racer2 = racers.get(1);
        assertEquals("R2", racer2.getAbbreviation());
        assertEquals("Racer 2", racer2.getRacerName());
        assertEquals("Team B", racer2.getTeam());
    }

    @Test
    void testReadLapTimes() throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testRacersFilePath))) {
            writer.write("R1_Racer_1Team\n");
            writer.write("R2_Racer_2Team\n");
        }

        try (BufferedWriter startLogWriter = new BufferedWriter(new FileWriter(testStartLogFilePath));
             BufferedWriter endLogWriter = new BufferedWriter(new FileWriter(testEndLogFilePath))) {
            startLogWriter.write("R1|2023-10-26_13:15:30.500\n");
            startLogWriter.write("R2|2023-10-26_13:20:45.750\n");

            endLogWriter.write("R1|2023-10-26_13:18:45.500\n");
            endLogWriter.write("R2|2023-10-26_13:23:10.750\n");
        }

        List<Racer> racers = raceDataReader.readRacersFromFile(testRacersFilePath);
        List<LapTime> lapTimes = raceDataReader.readLapTimes(racers, testStartLogFilePath, testEndLogFilePath);

        // Assert the number of lap times.
        assertEquals(2, lapTimes.size());

    }
}