package com.bkryvetskyi.service.calculation;

import java.time.Duration;
import java.time.LocalDateTime;

public class IntervalCalculator {
    /**
     * Calculates the interval between two LocalDateTime instances.
     * @param startTime The starting LocalDateTime.
     * @param endTime The ending LocalDateTime.
     * @return The Duration representing the interval between the two LocalDateTime instances.
     */
    public Duration calculateInterval(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime);
    }
}
