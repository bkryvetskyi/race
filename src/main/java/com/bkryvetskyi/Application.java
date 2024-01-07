package com.bkryvetskyi;

import com.bkryvetskyi.model.LapTime;
import com.bkryvetskyi.model.Racer;
import com.bkryvetskyi.service.ReportProcessor;

import java.util.List;

import com.bkryvetskyi.service.data.DataFileReader;
import com.bkryvetskyi.service.formatting.ResultFormatter;
import com.bkryvetskyi.service.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Application class is the entry point for the racing application.
 */
public class Application {
    private static final Logger LOGGER = LogManager.getLogger(Application.class);
    private static final String START_LOG_FILE_NAME = "start.log";
    private static final String END_LOG_FILE_NAME = "end.log";
    private static final String RACERS_FILE_NAME = "abbreviations.txt";

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        loadRacerData();
    }

    public static void loadRacerData() {
        DataFileReader dataFileReader = new DataFileReader();
        Parser parser = new Parser();

        List<String> lines = dataFileReader.readFileLines(RACERS_FILE_NAME);
        List<Racer> racers = parser.parseRacers(lines);

        List<String> startLinesLapTime = dataFileReader.readFileLines(START_LOG_FILE_NAME);
        List<String> endLinesLapTime = dataFileReader.readFileLines(END_LOG_FILE_NAME);

        ReportProcessor reportProcessor = new ReportProcessor();

        List<LapTime> startLapTimes = parser.parseDateTimes(startLinesLapTime);
        List<LapTime> endLapTimes = parser.parseDateTimes(endLinesLapTime);

        List<LapTime> findLapDurationStartAndEndLapTimes =
                reportProcessor.lapDurationTime(racers, startLapTimes, endLapTimes);
        List<LapTime> sortedRacerByLapDuration = reportProcessor.sortByLaDuration(findLapDurationStartAndEndLapTimes);

        ResultFormatter resultFormatter = new ResultFormatter();
        String formattedResult = resultFormatter.formatResults(sortedRacerByLapDuration);
        LOGGER.info(formattedResult);
    }
}
