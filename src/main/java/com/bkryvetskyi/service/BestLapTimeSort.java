package com.bkryvetskyi.service;

import com.bkryvetskyi.model.LapTime;
import com.bkryvetskyi.service.calculation.LapTimeUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BestLapTimeSort {
    /**
     * Sorts the best lap times in ascending order.
     * @param lapTimes The list of lap times to sort.
     * @return A sorted list of best lap times.
     */
    public List<LapTime> sortBestLapTimes(List<LapTime> lapTimes) {
        LapTimeUtils timeUtils = new LapTimeUtils();
        List<LapTime> bestLapTimes = timeUtils.getBestLapTime(lapTimes);

        return bestLapTimes.stream()
                .sorted(Comparator.comparing(LapTime::getLapDuration))
                .collect(Collectors.toList());
    }
}
