package com.bkryvetskyi;

import com.bkryvetskyi.model.LapTime;
import com.bkryvetskyi.model.Racer;
import com.bkryvetskyi.service.data.DataReader;
import com.bkryvetskyi.service.calculation.LapTimeUtils;
import com.bkryvetskyi.service.data.RaceDataReader;

import java.util.Comparator;
import java.util.List;

import com.bkryvetskyi.service.formatting.ResultFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.stream.Collectors;

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

    /**
     * Runs the racing application, including loading data, calculating best lap times, and formatting results.
     */
    private static void run() {
        ResultFormatter resultFormatter = new ResultFormatter();
        DataReader daraReader = new RaceDataReader();
        List<LapTime> lapTimes = loadRacerData(daraReader);
        List<LapTime> bestLapTimes = sortBestLapTimes(lapTimes);
        String formattedResults = resultFormatter.formatResults(bestLapTimes);

        LOGGER.info(formattedResults);
    }

    /**
     * Loads racer data from input files.
     * @param 'dataReader' The data reader to use for loading data.
     * @return A list of lap times.
     */
    private static List<LapTime> loadRacerData(DataReader daraReader) {

        List<Racer> racers = daraReader.readRacersFromFile(RACERS_FILE_NAME);

        return daraReader.readLapTimes(racers, START_LOG_FILE_NAME, END_LOG_FILE_NAME);
    }

    /**
     * Sorts the best lap times in ascending order.
     * @param lapTimes The list of lap times to sort.
     * @return A sorted list of best lap times.
     */
    private static List<LapTime> sortBestLapTimes(List<LapTime> lapTimes) {
        LapTimeUtils timeUtils = new LapTimeUtils();
        List<LapTime> bestLapTimes = timeUtils.getBestLapTime(lapTimes);

        return bestLapTimes.stream()
                .sorted(Comparator.comparing(LapTime::getLapDuration))
                .collect(Collectors.toList());
    }
}
