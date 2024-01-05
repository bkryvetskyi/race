package com.bkryvetskyi.model;

public class Racer {
    private String abbreviation;
    private String racerName;
    private String team;

    public Racer(String abbreviation, String racerName, String team) {
        this.abbreviation = abbreviation;
        this.racerName = racerName;
        this.team = team;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getRacerName() {
        return racerName;
    }

    public void setRacerName(String racerName) {
        this.racerName = racerName;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
