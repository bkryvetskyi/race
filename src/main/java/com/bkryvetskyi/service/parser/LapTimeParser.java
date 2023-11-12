package com.bkryvetskyi.service.parser;

import com.bkryvetskyi.model.LapTime;
import com.bkryvetskyi.model.Racer;
import com.bkryvetskyi.service.RacerRepository;
import com.bkryvetskyi.service.calculation.IntervalCalculator;
import com.bkryvetskyi.service.data.DataFileReader;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LapTimeParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");

    /**
     * Parses the lap times for racers from start and end log files.
     * @param racers The list of racers.
     * @param startLogFileName The filename of the start log file.
     * @param endLogFileName The filename of the end log file.
     * @return A list of LapTime objects representing the racers' lap times.
     */
    public List<LapTime> parserLapTimes(List<Racer> racers, String startLogFileName, String endLogFileName) {
        List<LapTime> lapTimes = new ArrayList<>();

        IntervalCalculator intervalCalculator = new IntervalCalculator();
        RacerRepository racerRepository = new RacerRepository();
        DataFileReader fileReader = new DataFileReader();

        Iterator<String> startIterator = fileReader
                .readFileLines(startLogFileName)
                .iterator();
        Iterator<String> endIterator = fileReader
                .readFileLines(endLogFileName)
                .iterator();

        while (startIterator.hasNext() && endIterator.hasNext()) {
            String startLine = startIterator.next();
            String endLine = endIterator.next();

            Racer racer = racerRepository.findRacerByAbbreviation(racers, startLine.substring(0, 3));
            LocalDateTime startTime = LocalDateTime.parse(startLine.substring(3), FORMATTER);
            LocalDateTime endTime = LocalDateTime.parse(endLine.substring(3), FORMATTER);
            Duration lapDuration = intervalCalculator.calculateInterval(startTime, endTime);

            LapTime lapTime = new LapTime(racer, startTime, endTime, lapDuration);
            lapTimes.add(lapTime);
        }
        return lapTimes;
    }
}
