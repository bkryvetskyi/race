package com.bkryvetskyi;

import com.bkryvetskyi.model.LapTime;
import com.bkryvetskyi.model.Racer;
import com.bkryvetskyi.service.ReportProcessor;
import java.util.List;

import com.bkryvetskyi.service.formatting.ResultFormatter;
import com.bkryvetskyi.service.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Application class is the entry point for the racing application.
 */
public class  Application {
    private static final Logger LOGGER = LogManager.getLogger(Application.class);
    private static final String START_LOG_FILE_NAME = "start.log";
    private static final String END_LOG_FILE_NAME = "end.log";
    private static final String RACERS_FILE_NAME = "abbreviations.txt";

    public static void main(String[] args) {
        run();
    }

    /**
     * Runs the racing application, including loading data, calculating best lap times, and formatting results.
     */
    private static void run() {
        ResultFormatter resultFormatter = new ResultFormatter();
        ReportProcessor reportProcessor = new ReportProcessor();
        List<LapTime> lapTimes = loadRacerData();
        List<LapTime> bestLapTimes = reportProcessor.sortBestLapTimes(lapTimes);
        String formattedResults = resultFormatter.formatResults(bestLapTimes);

        LOGGER.info(formattedResults);
    }

    /**
     * Loads racer data from input files.
     * @return A list of lap times.
     */
    private static List<LapTime> loadRacerData() {
        Parser racersParser = new Parser();
        ReportProcessor reportProcessor = new ReportProcessor();
        List<Racer> racers = racersParser.parseRacers(RACERS_FILE_NAME);

        return reportProcessor.processLapTimesFromLogs(racers, START_LOG_FILE_NAME, END_LOG_FILE_NAME);
    }
}
