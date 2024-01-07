package com.bkryvetskyi.model;

public class Racer {
    private final String abbreviation;
    private final String racerName;
    private final String team;

    public Racer(String abbreviation, String racerName, String team) {
        this.abbreviation = abbreviation;
        this.racerName = racerName;
        this.team = team;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getRacerName() {
        return racerName;
    }

    public String getTeam() {
        return team;
    }
}
