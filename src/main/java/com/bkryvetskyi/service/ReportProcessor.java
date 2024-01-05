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

	public List<LapTime> lapDurationTime(List<LapTime> startLapTimes, List<LapTime> endLapTimes) {
		List<LapTime> listLapTimes = new ArrayList<>();
		int size = Math.min(startLapTimes.size(), endLapTimes.size());
		Duration lapDuration;

		for (int i = 0; i < size; i++) {
			LapTime startLapTime = startLapTimes.get(i);
			LapTime endLapTime = endLapTimes.get(i);

			lapDuration = calculateLapTime(startLapTime, endLapTime);
			LapTime lapTimeWithDuration = new LapTime(startLapTime.getRacer(), lapDuration);
			listLapTimes.add(lapTimeWithDuration);
		}

		return listLapTimes;
	}

	private Duration calculateLapTime(LapTime start, LapTime end) {
		return Duration.between(start.getDateTime(), end.getDateTime());
	}

	public List<LapTime> findRacerByAbbreviation(List<Racer> racers, List<LapTime> lapTimes) {
		return lapTimes.stream()
				.filter(lapTime -> racers.stream()
						.anyMatch(racer -> racer.getAbbreviation().equals(lapTime.getRacer().getAbbreviation())))
				.collect(Collectors.toList());
	}

	public List<LapTime> sortedByLaDuration(List<LapTime> listRacers) {
		return listRacers.stream()
				.sorted(Comparator.comparing(LapTime::getLapDuration))
				.collect(Collectors.toList());
	}

	public List<LapTime> sortLapTimesByAbbreviation(List<LapTime> lapTimes) {
		return lapTimes.stream()
				.sorted(Comparator.comparing(lapTime -> lapTime.getRacer().getAbbreviation()))
				.collect(Collectors.toList());
	}
}