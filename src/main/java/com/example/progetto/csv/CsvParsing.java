package com.example.progetto.csv;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

public class CsvParsing {

    public void display(ArrayList<CsvRow> array){
        for(CsvRow c: array) {
            System.out.println(c);
        }
    }

     public String cvsSplitByStd = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
     ArrayList<CsvRow> rows= new ArrayList<CsvRow>();

    public ArrayList<CsvRow> parser(String link_csv) throws IOException {

        URL url = new URL(link_csv);
        URLConnection connection = url.openConnection();
        String line = "";
        FetchCsvData GetDataRow=new FetchCsvData();
        /*todo: costruttori stile mancini*/
        CsvRow objrow = new CsvRow();
        int count=0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())))
        {
            while ((line = GetDataRow.getLine(br)) != null) {
                if(count==0) {
                    count++;
                    continue;
                }
                count++;
                String[] row = GetDataRow.splitLine(line,cvsSplitByStd);
                objrow.setCountry(row[0]);
                objrow.setCountry_codes(row[1],row[2],row[3]);
                objrow.setFund(row[4]);
                objrow.setYear(Integer.parseInt(row[5]));
                objrow.setField(row[6]);
                if(row[7].equals(""))
                    row[7]="0";
                objrow.setEU_Payment_annual(Integer.parseInt(row[7]));
                objrow.setModelled_annual_expenditure(Integer.parseInt(row[8]));
                objrow.setStandard_Deviation_of_annual_expenditure(Integer.parseInt(row[9]));
                objrow.setStandard_Error_of_modelled_annual_expenditure(Integer.parseInt(row[10]));
                rows.add(objrow);
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found. Check your internet connection.");
        }
        catch (EOFException e){
            System.out.println("lettura del file terminata correttamente");
        }

        return rows;
    }

}

class CsvRow{
    private String Country,Fund,Dash="-";
    private int Year;
    private int EU_Payment_annual,Modelled_annual_expenditure,Standard_Deviation_of_annual_expenditure,Standard_Error_of_modelled_annual_expenditure;
    Programming_Period fields = new Programming_Period();
    Country_NUTS Country_codes = new Country_NUTS();
/*todo:modifiche mancini*/
    /*public CsvRow(int Year){
        this.Year = Year;
    }*/

    @Override
    public String toString() {
        return "CsvRow{" +
                "Country='" + Country + '\'' +
                ", Fund='" + Fund + '\'' +
                ", Dash='" + Dash + '\'' +
                ", Year=" + Year +
                ", EU_Payment_annual=" + EU_Payment_annual +
                ", Modelled_annual_expenditure=" + Modelled_annual_expenditure +
                ", Standard_Deviation_of_annual_expenditure=" + Standard_Deviation_of_annual_expenditure +
                ", Standard_Error_of_modelled_annual_expenditure=" + Standard_Error_of_modelled_annual_expenditure +
                ", fields=" + fields +
                ", Country_codes=" + Country_codes +
                '}';
    }

    public void setField(String field) throws IOException {

        FetchCsvData fetch = new FetchCsvData();
        String []stringvalues=new String[2];
        stringvalues=(fetch.splitLine(field,Dash));
        int[] yearvalues =new int[stringvalues.length];
        for(int i=0; i<yearvalues.length;i++)
        {
           yearvalues[i]=Integer.parseInt(stringvalues[i]) ;
        }
        fields.setProgrammingPeriodStart(yearvalues[0]);
        fields.setProgrammingPeriodEnd(yearvalues[1]);
    }

    public Programming_Period getFields() {
        return fields;
    }

    public void setCountry_codes(String NUTS1_ID, String NUTS2_ID, String NUTS2_name) {
        Country_codes.setNUTS1_ID(NUTS1_ID);
        Country_codes.setNUTS2_ID(NUTS2_ID);
        Country_codes.setNUTS2_name(NUTS2_name);
    }

    public Country_NUTS getCountry_codes() {
        return Country_codes;
    }

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

    public int getYear(){
        return Year;
    }

    public void setYear(int year) {
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

    @Override
    public String toString() {
        return "Programming_Period{" +
                "ProgrammingPeriodStart=" + ProgrammingPeriodStart +
                ", ProgrammingPeriodEnd=" + ProgrammingPeriodEnd +
                '}';
    }

    private int ProgrammingPeriodStart, ProgrammingPeriodEnd;

    public int getProgrammingPeriodStart() {
        return ProgrammingPeriodStart;
    }

    public void setProgrammingPeriodStart(int programmingPeriodStart) {
        ProgrammingPeriodStart = programmingPeriodStart;
    }

    public int getProgrammingPeriodEnd() {
        return ProgrammingPeriodEnd;
    }

    public void setProgrammingPeriodEnd(int programmingPeriodEnd) {
        ProgrammingPeriodEnd = programmingPeriodEnd;
    }
}

/*Nomenclatura delle Unità territoriali statistiche dell'EU*/
class Country_NUTS{

    @Override
    public String toString() {
        return "Country_NUTS{" +
                "NUTS1_ID='" + NUTS1_ID + '\'' +
                ", NUTS2_ID='" + NUTS2_ID + '\'' +
                ", NUTS2_name='" + NUTS2_name + '\'' +
                '}';
    }

    private String NUTS1_ID,NUTS2_ID,NUTS2_name;

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

    public String getNUTS2_name() {
        return NUTS2_name;
    }

    public void setNUTS2_name(String NUTS2_name) {
        this.NUTS2_name = NUTS2_name;
    }
}