package com.bkryvetskyi.service.data;


import com.bkryvetskyi.model.LapTime;
import com.bkryvetskyi.model.Racer;
import com.bkryvetskyi.service.RacerRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * The RaceDataReader class is responsible for reading racer data and lap times from input files.
 */
public class RaceDataReader implements DataReader {
    private static final Logger LOGGER = Logger.getLogger(RaceDataReader.class.getName());
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");

    /**
     * Reads lap times from start and end log files, matching them to racers based on abbreviations.
     * @param racers           The list of racers for matching abbreviations.
     * @param startLogFileName The filename for the start log file.
     * @param endLogFileName   The filename for the end log file.
     * @return A list of LapTime objects representing the lap times of racers.
     */
    public List<LapTime> readLapTimes(List<Racer> racers, String startLogFileName, String endLogFileName) {
        List<LapTime> lapTimes = new ArrayList<>();
        RacerRepository racerRepository = new RacerRepository();

        try (Stream<String> startLines = Files.lines(Paths.get(startLogFileName));
             Stream<String> endLines = Files.lines(Paths.get(endLogFileName))) {

            Iterator<String> startIterator = startLines.iterator();
            Iterator<String> endIterator = endLines.iterator();

            while (startIterator.hasNext() && endIterator.hasNext()) {
                String startLine = startIterator.next();
                String endLine = endIterator.next();

                Racer racer = racerRepository.findRacerByAbbreviation(racers, startLine.substring(0, 3));
                LocalDateTime startTime = LocalDateTime.parse(startLine.substring(3), FORMATTER);
                LocalDateTime endTime = LocalDateTime.parse(endLine.substring(3), FORMATTER);
                Duration lapDuration = Duration.between(startTime, endTime);

                LapTime lapTime = new LapTime(racer, startTime, endTime, lapDuration);
                lapTimes.add(lapTime);
            }
        } catch (IOException e) {
            LOGGER.warning("File read error! " + e.getMessage());
        }
        return lapTimes;
    }

    /**
     * Reads a list of racers from an input file.
     * @param fileName The filename of the input file containing racer information.
     * @return A list of Racer objects representing the racers' data.
     */
    public List<Racer> readRacersFromFile(String fileName) {
        List<Racer> racers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader (fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Splitting the line into abbreviation, name and team.
                String[] parts = line.split("_");
                if (parts.length >= 3) {
                    String abbreviation = parts[0];
                    String racerName = parts[1];
                    String team = parts[2];
                    Racer racer = new Racer(abbreviation, racerName, team);
                    racers.add(racer);
                }
            }
        } catch (IOException e) {
            LOGGER.warning("File read error! " + e.getMessage());
        }

        return racers;
    }
}
