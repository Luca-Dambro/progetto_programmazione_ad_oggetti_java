package com.example.progetto;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.Year;

public class CsvParsing {

    public void parser(String link_csv) throws IOException {


    }

}

class CsvRow{
    private String Country,Fund;
    private Year Year;
    private int EU_Payment_annual,Modelled_annual_expenditure,Standard_Deviation_of_annual_expenditure,Standard_Error_of_modelled_annual_expenditure;
    Programming_Period field = new Programming_Period();
    Country_NUTS Country_codes = new Country_NUTS();
}

class Programming_Period{
    private Year ProgrammingPeriodStart, ProgrammingPeriodEnd;

    public Year getProgrammingPeriodStart() {
        return ProgrammingPeriodStart;
    }

    public Year getProgrammingPeriodEnd() {
        return ProgrammingPeriodEnd;
    }
}

/*Nomenclatura delle Unità territoriali statiche dell'EU*/
class Country_NUTS{
    private String NUTS1_ID,NUTS2_ID,NUTS1_name;

    public String getNUTS1_ID() {
        return NUTS1_ID;
    }

    public String getNUTS2_ID() {
        return NUTS2_ID;
    }

    public String getNUTS1_name() {
        return NUTS1_name;
    }
}