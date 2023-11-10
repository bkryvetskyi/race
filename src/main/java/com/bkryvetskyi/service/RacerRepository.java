package com.bkryvetskyi.service;

import com.bkryvetskyi.model.Racer;

import java.util.List;

/**
 * The RacerRepository class provides a method to find a racer by their abbreviation.
 */
public class RacerRepository {
    /**
     * Finds a racer from the given list by their abbreviation.
     * @param racers The list of racers to search in.
     * @param abbreviation The abbreviation of the racer to find.
     * @return The racer with the specified abbreviation, or null if not found.
     */
    public Racer findRacerByAbbreviation(List<Racer> racers, String abbreviation) {
        return racers.stream()
                .filter(r -> r.getAbbreviation().equals(abbreviation))
                .findFirst()
                .orElse(null);
    }
}
