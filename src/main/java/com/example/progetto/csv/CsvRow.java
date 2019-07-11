package com.example.progetto.csv;

import java.io.IOException;

 class CsvRow
    {
        private String Country,Fund,Dash="-";
        private int Year;
        private int EU_Payment_annual,Modelled_annual_expenditure,Standard_Deviation_of_annual_expenditure,Standard_Error_of_modelled_annual_expenditure;
        Programming_Period Period = new Programming_Period();
        Country_NUTS Country_codes = new Country_NUTS();

        public void setPeriod(String period) throws IOException {

            CsvUtilities fetch = new CsvUtilities();
            String []stringvalues=new String[2];
            stringvalues=(fetch.splitLine(period,Dash));
            int[] yearvalues =new int[stringvalues.length];
            for(int i=0; i<yearvalues.length;i++)
            {
                yearvalues[i]=Integer.parseInt(stringvalues[i]) ;
            }

            Period.setProgrammingPeriodStart(yearvalues[0]);
            Period.setProgrammingPeriodEnd(yearvalues[1]);
        }

        public int[] getPeriod() {
            int[] app= new int[2];
            app[0]=Period.getProgrammingPeriodStart();
            app[1]=Period.getProgrammingPeriodEnd();
            return app;
        }

        public void setCountry_codes(String NUTS1_ID, String NUTS2_ID, String NUTS2_name) {
            Country_codes.setNUTS1_ID(NUTS1_ID);
            Country_codes.setNUTS2_ID(NUTS2_ID);
            Country_codes.setNUTS2_name(NUTS2_name);
        }

        public String[] getCountry_codes() {
            String[] app= new String[3];
            app[0]= Country_codes.getNUTS1_ID();
            app[1]=Country_codes.getNUTS2_ID();
            app[2]=Country_codes.getNUTS2_name();
            return app;
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

        public void setYear(String year) {

            Year = Integer.parseInt(year);
        }


        public int getEU_Payment_annual(){
            return EU_Payment_annual;
        }

        public void setEU_Payment_annual(String EU_Payment_annual) {
            if(EU_Payment_annual.equals(""))
            {
                EU_Payment_annual="0";
            }
            this.EU_Payment_annual = Integer.parseInt(EU_Payment_annual);
        }

        public int getModelled_annual_expenditure() {
            return Modelled_annual_expenditure;
        }

        public void setModelled_annual_expenditure(String modelled_annual_expenditure) {
            Modelled_annual_expenditure = Integer.parseInt(modelled_annual_expenditure);
        }

        public int getStandard_Deviation_of_annual_expenditure() {
            return Standard_Deviation_of_annual_expenditure;
        }

        public void setStandard_Deviation_of_annual_expenditure(String standard_Deviation_of_annual_expenditure) {
            Standard_Deviation_of_annual_expenditure = Integer.parseInt(standard_Deviation_of_annual_expenditure);
        }

        public int getStandard_Error_of_modelled_annual_expenditure() {
            return Standard_Error_of_modelled_annual_expenditure;
        }

        public void setStandard_Error_of_modelled_annual_expenditure(String standard_Error_of_modelled_annual_expenditure) {
            Standard_Error_of_modelled_annual_expenditure = Integer.parseInt(standard_Error_of_modelled_annual_expenditure);
        }


        public String toString() {
            CsvUtilities fetch = new CsvUtilities();


            return "CsvRow{" +
                    "Country='" + Country + '\'' +
                    ", Fund='" + Fund + '\'' +
                    ", Year=" + Year +
                    ", EU_Payment_annual=" + EU_Payment_annual +
                    ", Modelled_annual_expenditure=" + Modelled_annual_expenditure +
                    ", Standard_Deviation_of_annual_expenditure=" + Standard_Deviation_of_annual_expenditure +
                    ", Standard_Error_of_modelled_annual_expenditure=" + Standard_Error_of_modelled_annual_expenditure +
                    ", Programming_period=" + Period +
                    ", Country_codes=" + Country_codes +
                    '}';
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
