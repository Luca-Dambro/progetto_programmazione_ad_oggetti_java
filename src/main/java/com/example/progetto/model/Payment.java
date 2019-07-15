package com.example.progetto.model;

public class Payment {

    private String  Country,
                    NUTS1_ID,
                    NUTS2_ID,
                    NUTS2_name,
                    Fund;
    private Integer Year;
    private Programming_Period Period = new Programming_Period();
    private Integer EU_Payment_annual,
                    Modelled_annual_expenditure,
                    Standard_Deviation_of_annual_expenditure,
                    Standard_Error_of_modelled_annual_expenditure;


    /*getter and setter for country*/
    public String getCountry() {
        return Country;
    }
    public void setCountry(String country) {
        Country = country;
    }

    /*getter and setter for nuts1-id*/

    public String getNUTS1_ID() {
        return NUTS1_ID;
    }

    public void setNUTS1_ID(String NUTS1_ID) {
        this.NUTS1_ID = NUTS1_ID;
    }
    /*getter and setter for nuts2-id*/

    public String getNUTS2_ID() {
        return NUTS2_ID;
    }

    public void setNUTS2_ID(String NUTS2_ID) {
        this.NUTS2_ID = NUTS2_ID;
    }
    /*getter and setter for nuts2-name*/

    public String getNUTS2_name() {
        return NUTS2_name;
    }

    public void setNUTS2_name(String NUTS2_name) {
        this.NUTS2_name = NUTS2_name;
    }
    /*getter and setter for fund*/
    public String getFund() {
        return Fund;
    }

    public void setFund(String fund) {
        Fund = fund;
    }

    /*getter and setter for year*/
    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {

        Year = year;
    }

    /*getter and setter for programming period*/
    public String getPeriod() {
        String app = new String();
        app+= Period.getProgrammingPeriodStart();
        app+="-";
        app+=Period.getProgrammingPeriodEnd();
        return app;
    }

    public void setPeriod(Programming_Period period) {
        Period = period;
    }

    /*getter and setter for EU_Payment_annual*/
    public Integer getEU_Payment_annual() {
        return EU_Payment_annual;
    }
    public void setEU_Payment_annual(Integer EU_Payment_annual) {
        /*if (EU_Payment_annual.equals("")) {
            EU_Payment_annual = "0";
        }*/
        this.EU_Payment_annual = (EU_Payment_annual);
    }


    /*getter and setter for Modelled_annual_expenditure*/
    public Integer getModelled_annual_expenditure() {
        return Modelled_annual_expenditure;
    }
    public void setModelled_annual_expenditure(Integer modelled_annual_expenditure) {
        Modelled_annual_expenditure = (modelled_annual_expenditure);
    }

    /*getter and setter for Standard_Deviation_of_annual_expenditure*/
    public Integer getStandard_Deviation_of_annual_expenditure() {
        return Standard_Deviation_of_annual_expenditure;
    }
    public void setStandard_Deviation_of_annual_expenditure(Integer standard_Deviation_of_annual_expenditure) {
        Standard_Deviation_of_annual_expenditure = (standard_Deviation_of_annual_expenditure);
    }

    /*getter and setter for getStandard_Error_of_modelled_annual_expenditure*/
    public Integer getStandard_Error_of_modelled_annual_expenditure() {
        return Standard_Error_of_modelled_annual_expenditure;
    }
    public void setStandard_Error_of_modelled_annual_expenditure(Integer standard_Error_of_modelled_annual_expenditure) {
        Standard_Error_of_modelled_annual_expenditure = (standard_Error_of_modelled_annual_expenditure);
    }

    /*toString*/

    @Override
    public String toString() {
        return "Payment{" +
                "Country='" + Country + '\'' +
                ", NUTS1_ID='" + NUTS1_ID + '\'' +
                ", NUTS2_ID='" + NUTS2_ID + '\'' +
                ", NUTS2_name='" + NUTS2_name + '\'' +
                ", Fund='" + Fund + '\'' +
                ", Year=" + Year +
                ", Programming_Period=" + Period +
                ", EU_Payment_annual=" + EU_Payment_annual +
                ", Modelled_annual_expenditure=" + Modelled_annual_expenditure +
                ", Standard_Deviation_of_annual_expenditure=" + Standard_Deviation_of_annual_expenditure +
                ", Standard_Error_of_modelled_annual_expenditure=" + Standard_Error_of_modelled_annual_expenditure +
                '}';
    }
}



