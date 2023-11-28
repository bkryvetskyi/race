package com.bkryvetskyi.service.parser;

import com.bkryvetskyi.Application;
import com.bkryvetskyi.model.Racer;
import com.bkryvetskyi.service.data.DataFileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
    private static final Logger LOGGER = LogManager.getLogger(Parser.class);

    /**
     * Parses the information of racers from a file containing racer data.
     * @param fileName The name of the file containing racer information.
     * @return A list of Racer objects parsed from the file.
     */

    public List<Racer> parseRacers(String fileName) {
        List<Racer> racers = new ArrayList<>();

        DataFileReader dataFileReader = new DataFileReader();
        List<String> lines = dataFileReader.readFileLines(fileName);

        lines.forEach(line -> {
            String[] parts = line.split("_");
            if (parts.length >= 3) {
                String abbreviation = parts[0];
                String racerName = parts[1];
                String team = parts[2];
                Racer racer = new Racer(abbreviation, racerName, team);
                racers.add(racer);
            }
        });

        return racers;
    }

    public LocalDateTime parserDataTime(String dataTimeString) {
        if (isValidFormat(dataTimeString)) {
            return LocalDateTime.parse(dataTimeString, FORMATTER);
        } else {
            LOGGER.error("The format is not valid, " + dataTimeString);
            return null;
        }
    }

    private boolean isValidFormat(String dataTimeString) {
        String regex = "[A-Z]{3}\\d{4}-\\d{2}-\\d{2}_\\d{2}:\\d{2}:\\d{2}.\\d{3}";
        return dataTimeString.matches(regex);
    }
}
