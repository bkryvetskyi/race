package com.bkryvetskyi.service.parser;

import com.bkryvetskyi.model.Racer;
import com.bkryvetskyi.service.data.DataFileReader;

import java.util.ArrayList;
import java.util.List;

public class RacersParser {
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
}
