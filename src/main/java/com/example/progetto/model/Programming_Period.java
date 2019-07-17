package com.example.progetto.model;

/**
 * This class is used to abstract the a particolar data of a column of a csv
 * wich encapsulate two information.
 */
public class Programming_Period {

    private String ProgrammingPeriodStart,
            ProgrammingPeriodEnd;

    /*toString*/
    @Override
    public String toString() {
        return "Programming_Period{" +
                "ProgrammingPeriodStart=" + ProgrammingPeriodStart +
                ", ProgrammingPeriodEnd=" + ProgrammingPeriodEnd +
                '}';
    }

    //getter and setter for ProgrammingPeriod members
    public String getProgrammingPeriodStart() {
        return ProgrammingPeriodStart;
    }

    public void setProgrammingPeriodStart(String programmingPeriodStart) {
        ProgrammingPeriodStart = programmingPeriodStart;
    }

    public String getProgrammingPeriodEnd() {
        return ProgrammingPeriodEnd;
    }

    public void setProgrammingPeriodEnd(String programmingPeriodEnd) {
        ProgrammingPeriodEnd = programmingPeriodEnd;
    }
}