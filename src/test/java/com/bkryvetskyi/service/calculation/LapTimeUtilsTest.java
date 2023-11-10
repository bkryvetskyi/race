package com.bkryvetskyi.service.calculation;

import com.bkryvetskyi.model.LapTime;
import com.bkryvetskyi.model.Racer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LapTimeUtilsTest {
    private LapTimeUtils lapTimeUtils;

    @BeforeEach
    void setUp() {
        lapTimeUtils = new LapTimeUtils();
    }

    @Test
    void getBestLapTime() {
        List<LapTime> lapTimes = new ArrayList<>();
        Racer racer1 = new Racer("R1", "Racer 1", "Team A");
        Racer racer2 = new Racer("R2", "Racer 2", "Team B");

        // Adding lap times for two racers.
        lapTimes.add(new LapTime(racer1, LocalDateTime.now(), LocalDateTime.now().plusSeconds(10), Duration.ofSeconds(10)));
        lapTimes.add(new LapTime(racer2, LocalDateTime.now(), LocalDateTime.now().plusSeconds(5), Duration.ofSeconds(5)));
        lapTimes.add(new LapTime(racer1, LocalDateTime.now(), LocalDateTime.now().plusSeconds(9), Duration.ofSeconds(9)));
        lapTimes.add(new LapTime(racer2, LocalDateTime.now(), LocalDateTime.now().plusSeconds(7), Duration.ofSeconds(7)));
        lapTimes.add(new LapTime(racer1, LocalDateTime.now(), LocalDateTime.now().plusSeconds(8), Duration.ofSeconds(8)));

        List<LapTime> bestLapTimes = lapTimeUtils.getBestLapTime(lapTimes);

        assertEquals(2, bestLapTimes.size()); // Two racers should have best lap times.
        assertEquals(5, lapTimes.size()); // The original list should remain unchanged.

        // Check if best lap times are correctly selected.
        for (LapTime bestLapTime : bestLapTimes) {
            if (bestLapTime.getRacer().equals(racer1)) {
                assertEquals("00:08.000", bestLapTime.getLapDuration().toString()); // Use Duration.toString().
            } else if (bestLapTime.getRacer().equals(racer2)) {
                assertEquals("00:05.000", bestLapTime.getLapDuration().toString()); // Use Duration.toString().
            }
        }
    }
}