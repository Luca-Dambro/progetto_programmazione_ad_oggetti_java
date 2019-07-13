package com.example.progetto.model;

public class Programming_Period {

    private Integer ProgrammingPeriodStart,
            ProgrammingPeriodEnd;
    /*toString*/
    @Override
    public String toString() {
        return "Programming_Period{" +
                "ProgrammingPeriodStart=" + ProgrammingPeriodStart +
                ", ProgrammingPeriodEnd=" + ProgrammingPeriodEnd +
                '}';
    }

    /*getter and setter for ProgrammingPeriod members*/
    public Integer getProgrammingPeriodStart() {
        return ProgrammingPeriodStart;
    }

    public void setProgrammingPeriodStart(Integer programmingPeriodStart) {
        ProgrammingPeriodStart = programmingPeriodStart;
    }

    public Integer getProgrammingPeriodEnd() {
        return ProgrammingPeriodEnd;
    }

    public void setProgrammingPeriodEnd(Integer programmingPeriodEnd) {
        ProgrammingPeriodEnd = programmingPeriodEnd;
    }
}