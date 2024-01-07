package com.bkryvetskyi.service;

import com.bkryvetskyi.model.LapTime;
import com.bkryvetskyi.model.Racer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.stream.Collectors;

public class ReportProcessor {
    private static final Logger LOGGER = LogManager.getLogger(ReportProcessor.class);


	/**
	 * Calculates lap durations and associates them with the corresponding racers.
	 *
	 * @param racers        List of racers for whom lap durations need to be calculated.
	 * @param startLapTimes List of start lap times for each racer.
	 * @param endLapTimes   List of end lap times for each racer.
	 * @return List of LapTime objects containing racer information and lap durations.
	 */
    public List<LapTime> lapDurationTime(List<Racer> racers, List<LapTime> startLapTimes, List<LapTime> endLapTimes) {
        List<LapTime> listLapTimes = new ArrayList<>();
        int size = Math.min(startLapTimes.size(), endLapTimes.size());
        Duration lapDuration;

        for (int i = 0; i < size; i++) {
            LapTime startLapTime = startLapTimes.get(i);
            LapTime endLapTime = endLapTimes.get(i);

            lapDuration = calculateLapTime(startLapTime, endLapTime);
            Racer racer = findRacerByAbbreviation(startLapTime.getRacer().getAbbreviation(), racers);

            LapTime lapTimeWithDuration = new LapTime(racer, lapDuration);
            listLapTimes.add(lapTimeWithDuration);
        }

        LOGGER.info("lapDurationTime processed for {} laps.", size);

        return listLapTimes;
    }

    private Duration calculateLapTime(LapTime start, LapTime end) {
        return Duration.between(start.getDateTime(), end.getDateTime());
    }

	/**
	 * Finds a racer in the list based on the provided abbreviation.
	 *
	 * @param abbreviation Abbreviation of the racer to find.
	 * @param racers       List of racers to search within.
	 * @return Racer object with the specified abbreviation or null if not found.
	 */
    private Racer findRacerByAbbreviation(String abbreviation, List<Racer> racers) {
        return racers.stream()
                .filter(racer -> racer.getAbbreviation().equals(abbreviation))
                .findFirst()
                .orElse(null);
    }

	/**
	 * Sorts the list of LapTime objects based on lap duration in ascending order.
	 *
	 * @param listRacers List of LapTime objects to be sorted.
	 * @return Sorted list of LapTime objects.
	 */
    public List<LapTime> sortByLaDuration(List<LapTime> listRacers) {
        return listRacers.stream()
                .sorted(Comparator.comparing(LapTime::getLapDuration))
                .collect(Collectors.toList());
    }
}