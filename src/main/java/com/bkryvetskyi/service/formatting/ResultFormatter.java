package com.bkryvetskyi.service.formatting;

import com.bkryvetskyi.model.LapTime;

import java.util.List;

/**
 * The ResultFormatter class is responsible for formatting a list of best lap times as a string.
 */
public class ResultFormatter {
    /**
     * Formats a list of best lap times into a string with a specific layout.
     * @param bestLapTimes The list of best lap times to be formatted.
     * @return A formatted string representation of the best lap times.
     */
    public String formatResults(List<LapTime> bestLapTimes) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < bestLapTimes.size(); i++) {
            LapTime lapTime = bestLapTimes.get(i);
            String racerName = lapTime.getRacer().getRacerName();
            String team = lapTime.getRacer().getTeam();
            String lapDuration = lapTime.getLapDuration();

            String formattedLine = String.format("%-2s. %-20s | %-30s | %s", (i + 1),
                    racerName, team, lapDuration);
            stringBuilder.append(formattedLine).append(System.lineSeparator());

            if (i == 14)
                stringBuilder.append("------------------------------------------------------------------------")
                        .append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }
}
