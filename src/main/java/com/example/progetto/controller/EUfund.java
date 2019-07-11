package com.example.progetto.controller;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class EUfund {


    private String Country, Fund;
    private int Year;
    private int EU_Payment_annual, Modelled_annual_expenditure;
    private int Standard_Deviation_of_annual_expenditure, Standard_Error_of_modelled_annual_expenditure;

    public EUfund () {

    }

    public EUfund (String Country, String Fund, int Year, int EU_Payment_annual, int Modelled_annual_expenditure,
                   int Standard_Deviation_of_annual_expenditure, int Standard_Error_of_modelled_annual_expenditure) {


        this.Country = Country;
        this.Fund = Fund;
        this.Year = Year;
        this.EU_Payment_annual = EU_Payment_annual;
        this.Modelled_annual_expenditure = Modelled_annual_expenditure;
        this.Standard_Deviation_of_annual_expenditure = Standard_Deviation_of_annual_expenditure;
        this.Standard_Error_of_modelled_annual_expenditure = Standard_Error_of_modelled_annual_expenditure;

    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getFund() {
        return Fund;
    }

    public void setFund(String fund) {
        Fund = fund;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getEU_Payment_annual() {
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