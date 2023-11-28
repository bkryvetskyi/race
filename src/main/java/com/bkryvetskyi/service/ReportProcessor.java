package com.bkryvetskyi.service;

import com.bkryvetskyi.model.LapTime;
import com.bkryvetskyi.model.Racer;
import com.bkryvetskyi.service.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.lang3.SystemUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReportProcessor {
    private static final Logger LOGGER = LogManager.getLogger(ReportProcessor.class);

    /**
     * Processes lap times from log files.
     * @param racers             The list of racers.
     * @param startLogFileName   The path to the start log file.
     * @param endLogFileName     The path to the end log file.
     * @return A list of LapTime objects representing the lap times of the racers.
     */
    public List<LapTime> processLapTimesFromLogs(List<Racer> racers, String startLogFileName, String endLogFileName) {
        List<LapTime> lapTimes = new ArrayList<>();

        if (SystemUtils.IS_OS_WINDOWS) {
            startLogFileName = startLogFileName.replace("/", "\\");
            endLogFileName = endLogFileName.replace("/", "\\");
        }

        if (startLogFileName == null || endLogFileName == null) {
            LOGGER.error("File paths cannot be null.");
            return lapTimes;
        }

        Path startLogPath = Paths.get(startLogFileName);
        Path endLogPath = Paths.get(endLogFileName);

        if (!Files.exists(startLogPath) || !Files.exists(endLogPath)) {
            LOGGER.error("One or both log files do not exist.");
            return lapTimes;
        }

        if (!Files.isRegularFile(startLogPath) || !Files.isRegularFile(endLogPath)) {
            LOGGER.error("One or both paths lead to a directory.");
            return lapTimes;
        }

        try (Stream<String> startLines = Files.lines(startLogPath);
             Stream<String> endLines = Files.lines(endLogPath)) {

            Iterator<String> startIterator = startLines.iterator();
            Iterator<String> endIterator = endLines.iterator();

            while (startIterator.hasNext() && endIterator.hasNext()) {
                String startLine = startIterator.next();
                String endLine = endIterator.next();

                lapTimes.add(processLapTimes(racers, startLine, endLine));
            }
        } catch (IOException e) {
            LOGGER.error("File read error! " + e.getMessage());
        }

        return lapTimes;
    }

    /**
     * Processes lap times for a single pair of start and end log lines.
     * @param racers      The list of racers.
     * @param startLine   The start log line.
     * @param endLine     The end log line.
     * @return A LapTime object representing the lap time for a racer.
     */
    private LapTime processLapTimes(List<Racer> racers, String startLine, String endLine) {
        Parser dataTimeParser = new Parser();
        String abbreviation = startLine.substring(0, 3);

        if (!abbreviation.matches("[A-Z]{3}")) {
            LOGGER.warn("Invalid racer abbreviation format: " + abbreviation);
            return null;
        }

        LocalDateTime startTime = dataTimeParser.parserDataTime(startLine.substring(3));
        LocalDateTime endTime = dataTimeParser.parserDataTime(endLine.substring(3));

        if (startTime == null || endTime == null) {
            LOGGER.warn("Invalid start or end time format.");
            return null;
        }

        Duration lapDuration = calculateInterval(startTime, endTime);

        Racer racer = findRacerByAbbreviation(racers, abbreviation);

        if (racer == null) {
            LOGGER.warn("Racer not found for abbreviation: " + abbreviation);
            return null;
        }

        return new LapTime(racer, startTime, endTime, lapDuration);
    }

    /**
     * Finds a racer based on the abbreviation.
     * @param racers        The list of racers.
     * @param abbreviation  The racer's abbreviation.
     * @return The Racer object found, or null if not found.
     */
    private Racer findRacerByAbbreviation(List<Racer> racers, String abbreviation) {
        if (abbreviation == null || racers == null) {
            return null;
        }

        return racers.stream()
                .filter(r -> abbreviation.equals(r.getAbbreviation()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Calculates the interval between two LocalDateTime objects.
     * @param startTime  The start time.
     * @param endTime    The end time.
     * @return The Duration representing the interval.
     */
    private Duration calculateInterval(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            LOGGER.warn("Invalid start or end time for calculating interval.");
            return null;
        }

        return Duration.between(startTime, endTime);
    }

    /**
     * Gets the best lap times from a list of lap times.
     * @param lapTimes  The list of lap times.
     * @return A list of LapTime objects representing the best lap times.
     */
    private List<LapTime> getBestLapTime(List<LapTime> lapTimes) {
        if (lapTimes == null) {
            LOGGER.warn("Cannot get best lap times from null lapTimes.");
            return Collections.emptyList();
        }

        return lapTimes.stream()
                .collect(Collectors.groupingBy(LapTime::getLapDuration,
                        Collectors.minBy(Comparator.comparing(LapTime::getLapDuration))))
                .values().stream()
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * Sorts the best lap times in ascending order.
     * @param lapTimes  The list of lap times.
     * @return A sorted list of LapTime objects representing the best lap times.
     */
    public List<LapTime> sortBestLapTimes(List<LapTime> lapTimes) {
        List<LapTime> bestLapTimes = getBestLapTime(lapTimes);

        return bestLapTimes.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(LapTime::getLapDuration))
                .collect(Collectors.toList());
    }
}