package com.example.progetto.csv;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.Year;

public class CsvParsing {

    public void parser(String link_csv) throws IOException {

        URL url = new URL(link_csv);
        URLConnection connection = url.openConnection();
        String line = "";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())))
        {
            CsvRow csvrow = new CsvRow();
            FetchCsvData GetDataRow=new FetchCsvData();
            while ((line = GetDataRow.getLine(br)) != null) {
                String[] row = GetDataRow.splitLine(line);
                for(int i=0; i<row.length; i++) {

                }
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found. Check your internet connection.");
        }
        catch (EOFException e){
            System.out.println("lettura del file terminata correttamente");
        }
    }

}

class CsvRow{
    private String Country,Fund;
    private Year Year;
    private int EU_Payment_annual,Modelled_annual_expenditure,Standard_Deviation_of_annual_expenditure,Standard_Error_of_modelled_annual_expenditure;
    Programming_Period field = new Programming_Period();
    Country_NUTS Country_codes = new Country_NUTS();

    public String getCountry(){
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getFund(){
        return Fund;
    }

    public void setFund(String fund) {
        Fund = fund;
    }

    public Year getYear(){
        return Year;
    }

    public void setYear(java.time.Year year) {
        Year = year;
    }


    public int getEU_Payment_annual(){
        return EU_Payment_annual;
    }

    public void setEU_Payment_annual(int EU_Payment_annual) {
        this.EU_Payment_annual = EU_Payment_annual;
    }

    public int getModelled_annual_expenditure() {
        return Modelled_annual_expenditure;
    }

    public void setModelled_annual_expenditure(int modelled_annual_expenditure) {
        Modelled_annual_expenditure = modelled_annual_expenditure;
    }

    public int getStandard_Deviation_of_annual_expenditure() {
        return Standard_Deviation_of_annual_expenditure;
    }

    public void setStandard_Deviation_of_annual_expenditure(int standard_Deviation_of_annual_expenditure) {
        Standard_Deviation_of_annual_expenditure = standard_Deviation_of_annual_expenditure;
    }

    public int getStandard_Error_of_modelled_annual_expenditure() {
        return Standard_Error_of_modelled_annual_expenditure;
    }

    public void setStandard_Error_of_modelled_annual_expenditure(int standard_Error_of_modelled_annual_expenditure) {
        Standard_Error_of_modelled_annual_expenditure = standard_Error_of_modelled_annual_expenditure;
    }
    
}

class Programming_Period{
    private Year ProgrammingPeriodStart, ProgrammingPeriodEnd;

    public Year getProgrammingPeriodStart() {
        return ProgrammingPeriodStart;
    }

    public void setProgrammingPeriodStart(Year programmingPeriodStart) {
        ProgrammingPeriodStart = programmingPeriodStart;
    }

    public Year getProgrammingPeriodEnd() {
        return ProgrammingPeriodEnd;
    }

    public void setProgrammingPeriodEnd(Year programmingPeriodEnd) {
        ProgrammingPeriodEnd = programmingPeriodEnd;
    }
}

/*Nomenclatura delle Unità territoriali statistiche dell'EU*/
class Country_NUTS{
    private String NUTS1_ID,NUTS2_ID,NUTS1_name;

    public String getNUTS1_ID() {
        return NUTS1_ID;
    }

    public void setNUTS1_ID(String NUTS1_ID) {
        this.NUTS1_ID = NUTS1_ID;
    }

    public String getNUTS2_ID() {
        return NUTS2_ID;
    }

    public void setNUTS2_ID(String NUTS2_ID) {
        this.NUTS2_ID = NUTS2_ID;
    }

    public String getNUTS1_name() {
        return NUTS1_name;
    }

    public void setNUTS1_name(String NUTS1_name) {
        this.NUTS1_name = NUTS1_name;
    }
}