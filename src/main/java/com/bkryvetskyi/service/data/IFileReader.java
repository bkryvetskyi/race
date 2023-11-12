package com.bkryvetskyi.service.data;

import com.bkryvetskyi.model.LapTime;
import com.bkryvetskyi.model.Racer;

import java.util.List;

public interface DataReader {
    List<LapTime> readLapTimes(List<Racer> racers, String startLogFileName, String endLogFileName);
    List<Racer> readRacersFromFile(String fileName);
}
