package com.bkryvetskyi.service.formatting;

import com.bkryvetskyi.model.LapTime;
import com.bkryvetskyi.model.Racer;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResultFormatterTest {

    @Test
    void formatResults() {
        ResultFormatter resultFormatter = new ResultFormatter();

        List<LapTime> bestLapTimes = new ArrayList<>();
        bestLapTimes.add(new LapTime(new Racer("RT1", "Racer1", "Team1"),
                null, null, Duration.ofMinutes(1).plusSeconds(30)));
        bestLapTimes.add(new LapTime(new Racer("RT2", "Racer2", "Team2"),
                null, null, Duration.ofMinutes(1).plusSeconds(45)));

        String expected = "1 . Racer1               | Team1                          | 01:30.000\r\n" +
                "2 . Racer2               | Team2                          | 01:45.000\r\n";

        String actual = resultFormatter.formatResults(bestLapTimes);
        assertEquals(expected, actual);
    }
}