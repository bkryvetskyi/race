package com.bkryvetskyi.service.parser;

import com.bkryvetskyi.model.LapTime;
import com.bkryvetskyi.model.Racer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static java.util.stream.Collectors.toList;


public class Parser {
    private static final Logger LOGGER = LogManager.getLogger(Parser.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
    private static final String UNDERLINE = "_";

    public List<LapTime> parseDateTimes(List<String> dateTimeString) {
        return dateTimeString.stream()
                .map(this::parseDataTime)
                .filter(Objects::nonNull)
                .collect(toList());
    }

    public LapTime parseDataTime(String dateTimeString) {
        if (isValidFormat(dateTimeString)) {
            String abbreviation = dateTimeString.substring(0, 3);
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString.substring(3), FORMATTER);

            return new LapTime(new Racer(abbreviation, null, null), dateTime);
        } else {
            throw new IllegalArgumentException("The format is not valid " + dateTimeString);
        }
    }

    public List<Racer> parseRacers(List<String> racerString) {
        return racerString.stream()
                .filter(this::isValidLine)
                .map(line -> line.split(UNDERLINE))
                .filter(this::isValidParts)
                .map(parts -> new Racer(parts[0], parts[1], parts[2]))
                .collect(toList());
    }

    private boolean isValidLine(String line) {
        boolean isValid = !StringUtils.isEmpty(line);

        if (!isValid)
            LOGGER.error("Invalid line: {}", line);

        return isValid;
    }

    private boolean isValidParts(String[] parts) {
        boolean isValid = parts.length >= 3 &&
                StringUtils.isNotBlank(parts[0]) &&
                StringUtils.isNotBlank(parts[1]) &&
                StringUtils.isNotBlank(parts[2]);

        if (!isValid)
            LOGGER.error("Invalid parts: {}", StringUtils.join(parts, ", "));

        return isValid;
    }

    private boolean isValidFormat(String dateTimeString) {
        String regex = "[A-Z]{3}\\d{4}-\\d{2}-\\d{2}_\\d{2}:\\d{2}:\\d{2}.\\d{3}";
        return dateTimeString.matches(regex);
    }
}
