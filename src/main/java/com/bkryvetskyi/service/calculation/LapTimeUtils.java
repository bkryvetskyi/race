package com.bkryvetskyi.service.calculation;

import com.bkryvetskyi.model.LapTime;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Utility class for lap time calculations.
 */
public class LapTimeUtils {
    /**
     * Finds the best lap time for each racer from a list of lap times.
     * @param lapTimes A list of LapTime objects representing lap times for multiple racers.
     * @return A list of LapTime objects, each representing the best lap time for a racer.
     */
    public List<LapTime> getBestLapTime(List<LapTime> lapTimes) {
        return lapTimes.stream()
                .collect(Collectors.groupingBy(LapTime::getRacer,
                        Collectors.minBy(Comparator.comparing(LapTime::getLapDuration))))
                .values().stream()
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
