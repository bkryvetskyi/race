package com.bkryvetskyi.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class LapTime {
    private Racer racer;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration lapDuration;

    public LapTime(Racer racer, LocalDateTime startTime, LocalDateTime endTime, Duration lapDuration) {
        this.racer = racer;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lapDuration = lapDuration;
    }

    public String getLapDuration() {
        long hours = Math.abs(lapDuration.toHours());
        long minutes = Math.abs(lapDuration.toMinutes() % 60);
        long second = Math.abs(lapDuration.getSeconds() % 60);
        long milliseconds = Math.abs(lapDuration.toMillis()) % 1000;

        if (hours == 0)
            return String.format("%02d:%02d.%03d", minutes, second, milliseconds);
        else
            return String.format("%02d:%02d:%02d.%03d", hours, minutes, second, milliseconds);
    }

    public Racer getRacer() {
        return racer;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
